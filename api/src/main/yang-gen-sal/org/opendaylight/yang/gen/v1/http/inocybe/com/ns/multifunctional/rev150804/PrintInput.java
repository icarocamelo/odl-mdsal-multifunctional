package org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.Augmentable;


/**
 * &lt;p&gt;This class represents the following YANG schema fragment defined in module &lt;b&gt;multifunctional&lt;/b&gt;
 * &lt;br&gt;(Source path: &lt;i&gt;META-INF/yang/multifunctional.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * container input {
 *     leaf numberOfPages {
 *         type uint32;
 *     }
 * }
 * &lt;/pre&gt;
 * The schema path to identify an instance is
 * &lt;i&gt;multifunctional/print/input&lt;/i&gt;
 *
 * &lt;p&gt;To create instances of this class use {@link org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInputBuilder}.
 * @see org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInputBuilder
 *
 */
public interface PrintInput
    extends
    DataObject,
    Augmentable<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput>
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.cachedReference(org.opendaylight.yangtools.yang.common.QName.create("http://inocybe.com/ns/multifunctional","2015-08-04","input"));

    /**
     * Number of page to be printed.
     *
     */
    java.lang.Long getNumberOfPages();

}

