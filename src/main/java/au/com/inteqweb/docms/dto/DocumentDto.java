package au.com.inteqweb.docms.dto;

import au.com.inteqweb.docms.constants.DocumentType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentDto {

    @JsonProperty
    private String filePath;

    @JsonProperty
    private DocumentType fileType;

}
