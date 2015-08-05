package org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804;
import org.opendaylight.yangtools.yang.binding.DataRoot;


/**
 * YANG version of the MULTIFUNCTIONAL-MIB.
 *
 * &lt;p&gt;This class represents the following YANG schema fragment defined in module &lt;b&gt;multifunctional&lt;/b&gt;
 * &lt;br&gt;Source path: &lt;i&gt;META-INF/yang/multifunctional.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * module multifunctional {
 *     yang-version 1;
 *     namespace "http://inocybe.com/ns/multifunctional";
 *     prefix "multifunctional";
 *
 *     revision 2015-08-04 {
 *         description "YANG version of the MULTIFUNCTIONAL-MIB.
 *         ";
 *     }
 *
 *     container multifunctional {
 *         leaf multifunctionalManufacturer {
 *             type DisplayString;
 *         }
 *         leaf multifunctionalModelNumber {
 *             type DisplayString;
 *         }
 *         leaf multifunctionalStatus {
 *             type enumeration;
 *         }
 *     }
 *
 *     identity hp-multifunctional {
 *         base "()IdentitySchemaNodeImpl[base=null, qname=(http://inocybe.com/ns/multifunctional?revision=2015-08-04)multifunctional-type]";
 *         description
 *             "HP multifunctional.";
 *         status CURRENT;
 *     }
 *     identity multifunctional-type {
 *         description
 *             "Base for all multifunctional types supported by the Multifunctional. New multifunctional types not listed here nay be added in the future.";
 *         status CURRENT;
 *     }
 *
 *     rpc cancel {
 *         "Stop printing.";
 *         status CURRENT;
 *     }
 *     rpc print {
 *         input {
 *             leaf numberOfPages {
 *                 type uint32;
 *             }
 *         }
 *         
 *         status CURRENT;
 *     }
 * }
 * &lt;/pre&gt;
 *
 */
public interface MultifunctionalData
    extends
    DataRoot
{




    /**
     * Top-level container for all multifunctional database objects.
     *
     */
    Multifunctional getMultifunctional();

}

