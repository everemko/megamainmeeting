package spring.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.*;

import java.io.IOException;

@Getter
@Setter
public class RpcRequest extends BaseRpc {

    private JsonNode params;
}
