package protocol;

import lombok.Data;

/**
 * @author jinxd
 */
@Data
public class RpcResponse {
    private String requestId;
    private String error;
    private Object result;
}
