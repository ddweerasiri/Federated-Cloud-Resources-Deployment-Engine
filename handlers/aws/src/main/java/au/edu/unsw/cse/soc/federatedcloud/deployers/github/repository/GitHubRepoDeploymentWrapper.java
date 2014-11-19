package au.edu.unsw.cse.soc.federatedcloud.deployers.github.repository;
/*
 * Copyright (c) 2014, Denis Weerasiri All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import au.edu.unsw.cse.soc.federatedcloud.datamodel.resource.CloudResourceDescription;
import au.edu.unsw.cse.soc.federatedcloud.deployers.CloudResourceDeployer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * User: denis
 * TODO: Include the class description here
 */
public class GitHubRepoDeploymentWrapper implements CloudResourceDeployer {
    private static final Logger log = LoggerFactory.getLogger(GitHubRepoDeploymentWrapper.class);
    protected static final String METHOD_POST = "POST";
    protected final String prefix;
    protected final String baseUri;
    private String credentials;
    String CHARSET_UTF8 = "UTF-8";
    protected static final String HEADER_AUTHORIZATION = "Authorization";
    protected static final String HEADER_USER_AGENT = "User-Agent";
    protected static final String HEADER_ACCEPT = "Accept";
    private String userAgent = USER_AGENT;
    protected Gson gson = new Gson();
    protected static final String HEADER_CONTENT_TYPE = "Content-Type";
    String CONTENT_TYPE_JSON = "application/json";
    private int bufferSize = 8192;
    protected static final String USER_AGENT = "GitHubJava/2.1.0";
    private int requestLimit = -1;
    private int remainingRequests = -1;

    public void deployResource(CloudResourceDescription description) throws Exception {
        HttpURLConnection request = createPost("/user/repos");
        Repository repository = new Repository();
        repository.setName(description.getName());
        repository.setDescription(description.getAttributes().get("description"));
        return sendJson(request, repository, Repository.class);
    }
    public void undeployResource(String resourceID) throws Exception {

    }
    protected HttpURLConnection createPost(String uri) throws IOException {
        return createConnection(uri, METHOD_POST);
    }
    protected HttpURLConnection createConnection(String uri) throws IOException {
        URL url = new URL(createUri(uri));
        return (HttpURLConnection) url.openConnection();
    }
    protected String createUri(final String path) {
        return baseUri + configureUri(path);
    }
    protected String configureUri(final String uri) {
        if (prefix == null || uri.startsWith(prefix))
            return uri;
        else
            return prefix + uri;
    }
    protected HttpURLConnection createConnection(String uri, String method)
            throws IOException {
        HttpURLConnection connection = createConnection(uri);
        connection.setRequestMethod(method);
        return configureRequest(connection);
    }
    protected HttpURLConnection configureRequest(final HttpURLConnection request) {
        if (credentials != null)
            request.setRequestProperty(HEADER_AUTHORIZATION, credentials);
        request.setRequestProperty(HEADER_USER_AGENT, userAgent);
        request.setRequestProperty(HEADER_ACCEPT,
                "application/vnd.github.beta+json"); //$NON-NLS-1$
        return request;
    }
    private <V> V sendJson(final HttpURLConnection request,
                           final Object params, final Type type) throws IOException {
        sendParams(request, params);
        final int code = request.getResponseCode();
        updateRateLimits(request);
        if (isOk(code))
            if (type != null)
                return parseJson(getStream(request), type);
            else
                return null;
        if (isEmpty(code))
            return null;
        throw createException(getStream(request), code,
                request.getResponseMessage());
    }
    protected InputStream getStream(HttpURLConnection request)
            throws IOException {
        if (request.getResponseCode() < HTTP_BAD_REQUEST)
            return request.getInputStream();
        else {
            InputStream stream = request.getErrorStream();
            return stream != null ? stream : request.getInputStream();
        }
    }
    protected IOException createException(InputStream response, int code,
                                          String status) {
        if (isError(code)) {
            final RequestError error;
            try {
                error = parseError(response);
            } catch (IOException e) {
                return e;
            }
            if (error != null)
                return new RequestException(error, code);
        } else
            try {
                response.close();
            } catch (IOException ignored) {
                // Ignored
            }
        String message;
        if (status != null && status.length() > 0)
            message = status + " (" + code + ')'; //$NON-NLS-1$
        else
            message = "Unknown error occurred (" + code + ')'; //$NON-NLS-1$
        return new IOException(message);
    }
    protected RequestError parseError(InputStream response) throws IOException {
        return parseJson(response, RequestError.class);
    }
    protected <V> V parseJson(InputStream stream, Type type, Type listType)
            throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                stream, CHARSET_UTF8), bufferSize);
        if (listType == null)
            try {
                return gson.fromJson(reader, type);
            } catch (JsonParseException jpe) {
                IOException ioe = new IOException(
                        "Parse exception converting JSON to object"); //$NON-NLS-1$
                ioe.initCause(jpe);
                throw ioe;
            } finally {
                try {
                    reader.close();
                } catch (IOException ignored) {
                    // Ignored
                }
            }
        else {
            JsonReader jsonReader = new JsonReader(reader);
            try {
                if (jsonReader.peek() == BEGIN_ARRAY)
                    return gson.fromJson(jsonReader, listType);
                else
                    return gson.fromJson(jsonReader, type);
            } catch (JsonParseException jpe) {
                IOException ioe = new IOException(
                        "Parse exception converting JSON to object"); //$NON-NLS-1$
                ioe.initCause(jpe);
                throw ioe;
            } finally {
                try {
                    jsonReader.close();
                } catch (IOException ignored) {
                    // Ignored
                }
            }
        }
    }
    protected <V> V parseJson(InputStream stream, Type type) throws IOException {
        return parseJson(stream, type, null);
    }
    protected boolean isEmpty(final int code) {
        return HTTP_NO_CONTENT == code;
    }
    protected boolean isOk(final int code) {
        switch (code) {
            case HTTP_OK:
            case HTTP_CREATED:
            case HTTP_ACCEPTED:
                return true;
            default:
                return false;
        }
    }
    protected void sendParams(HttpURLConnection request, Object params)
            throws IOException {
        request.setDoOutput(true);
        if (params != null) {
            request.setRequestProperty(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON
                    + "; charset=" + CHARSET_UTF8); //$NON-NLS-1$
            byte[] data = toJson(params).getBytes(CHARSET_UTF8);
            request.setFixedLengthStreamingMode(data.length);
            BufferedOutputStream output = new BufferedOutputStream(
                    request.getOutputStream(), bufferSize);
            try {
                output.write(data);
                output.flush();
            } finally {
                try {
                    output.close();
                } catch (IOException ignored) {
                    // Ignored
                }
            }
        } else {
            request.setFixedLengthStreamingMode(0);
            request.setRequestProperty("Content-Length", "0");
        }
    }
    protected String toJson(Object object) throws IOException {
        try {
            return gson.toJson(object);
        } catch (JsonParseException jpe) {
            IOException ioe = new IOException(
                    "Parse exception converting object to JSON"); //$NON-NLS-1$
            ioe.initCause(jpe);
            throw ioe;
        }
    }
    protected GitHubClient updateRateLimits(HttpURLConnection request) {
        String limit = request.getHeaderField("X-RateLimit-Limit");
        if (limit != null && limit.length() > 0)
            try {
                requestLimit = Integer.parseInt(limit);
            } catch (NumberFormatException nfe) {
                requestLimit = -1;
            }
        else
            requestLimit = -1;

        String remaining = request.getHeaderField("X-RateLimit-Remaining");
        if (remaining != null && remaining.length() > 0)
            try {
                remainingRequests = Integer.parseInt(remaining);
            } catch (NumberFormatException nfe) {
                remainingRequests = -1;
            }
        else
            remainingRequests = -1;

        return this;
    }
}
