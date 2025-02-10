package com.example.multiplegranarymanager.Bean.Login;

import java.util.List;

public class LoginNewDownRawBean {
    private int code;
    private String msg = "";
    private List<Data> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {
        private String url;
        private String method;
        private Object headers;
        private Object query;
        private Object body;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public Object getHeaders() {
            return headers;
        }

        public void setHeaders(Object headers) {
            this.headers = headers;
        }

        public Object getQuery() {
            return query;
        }

        public void setQuery(Object query) {
            this.query = query;
        }

        public Object getBody() {
            return body;
        }

        public void setBody(Object body) {
            this.body = body;
        }
    }
}
