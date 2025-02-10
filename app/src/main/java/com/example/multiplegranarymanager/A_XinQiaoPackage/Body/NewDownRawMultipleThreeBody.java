package com.example.multiplegranarymanager.A_XinQiaoPackage.Body;

import java.util.List;

public class NewDownRawMultipleThreeBody {
    private String url;
    private String method;
    private Object headers;
    private Object query;
    private ListBody body;

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
        if (headers == null) {
            this.headers = new Object();
        } else {
            this.headers = headers;
        }
    }

    public Object getQuery() {
        return query;
    }

    public void setQuery(Object query) {
        if (query == null) {
            this.query = new Object();
        } else {
            this.query = query;
        }
    }

    public ListBody getBody() {
        return body;
    }

    public void setBody(ListBody body) {
        this.body = body;
    }

    public static class list {
        private String commandContent;
        private String commandMapId;
        private String url;

        public String getCommandMapId() {
            return commandMapId;
        }

        public void setCommandMapId(String commandMapId) {
            this.commandMapId = commandMapId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCommandContent() {
            return commandContent;
        }

        public void setCommandContent(String commandContent) {
            this.commandContent = commandContent;
        }
    }

    public static class ListBody {
        private List<list> list;

        public List<NewDownRawMultipleThreeBody.list> getList() {
            return list;
        }

        public void setList(List<NewDownRawMultipleThreeBody.list> list) {
            this.list = list;
        }
    }
}
