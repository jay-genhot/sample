package no.trinnvis.dabih.api.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentUpdateMessage {
    private String category;
    private String template;
    private String name;
    private String status;
    private String content;
    private String type;

    @JsonCreator
    public DocumentUpdateMessage(@JsonProperty("category") String category, @JsonProperty("template") String template,
                                 @JsonProperty("name") String name, @JsonProperty("status") String status,
                                 @JsonProperty("content") String content, @JsonProperty("type") String type) {
        this.category = category;
        this.template = template;
        this.name = name;
        this.status = status;
        this.content = content;
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public String getTemplate() {
        return template;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

}
