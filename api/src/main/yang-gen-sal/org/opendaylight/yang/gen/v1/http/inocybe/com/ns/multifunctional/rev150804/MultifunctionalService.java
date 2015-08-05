package org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804;
import java.util.concurrent.Future;
import org.opendaylight.yangtools.yang.binding.RpcService;
import org.opendaylight.yangtools.yang.common.RpcResult;


/**
 * Interface for implementing the following YANG RPCs defined in module &lt;b&gt;multifunctional&lt;/b&gt;
 * &lt;br&gt;(Source path: &lt;i&gt;META-INF/yang/multifunctional.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * rpc cancel {
 *     "Stop printing.";
 *     status CURRENT;
 * }
 * rpc print {
 *     input {
 *         leaf numberOfPages {
 *             type uint32;
 *         }
 *     }
 *     
 *     status CURRENT;
 * }
 * &lt;/pre&gt;
 *
 */
public interface MultifunctionalService
    extends
    RpcService
{




    /**
     * Stop printing.
     *
     */
    Future<RpcResult<java.lang.Void>> cancel();
    
    Future<RpcResult<java.lang.Void>> print(PrintInput input);

}

