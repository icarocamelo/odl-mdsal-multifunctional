package org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.binding.ChildOf;
import org.opendaylight.yangtools.yang.binding.Augmentable;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional.MultifunctionalStatus;


/**
 * Top-level container for all multifunctional database objects.
 *
 * &lt;p&gt;This class represents the following YANG schema fragment defined in module &lt;b&gt;multifunctional&lt;/b&gt;
 * &lt;br&gt;(Source path: &lt;i&gt;META-INF/yang/multifunctional.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * container multifunctional {
 *     leaf multifunctionalManufacturer {
 *         type DisplayString;
 *     }
 *     leaf multifunctionalModelNumber {
 *         type DisplayString;
 *     }
 *     leaf multifunctionalStatus {
 *         type enumeration;
 *     }
 * }
 * &lt;/pre&gt;
 * The schema path to identify an instance is
 * &lt;i&gt;multifunctional/multifunctional&lt;/i&gt;
 *
 * &lt;p&gt;To create instances of this class use {@link org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.MultifunctionalBuilder}.
 * @see org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.MultifunctionalBuilder
 *
 */
public interface Multifunctional
    extends
    ChildOf<MultifunctionalData>,
    Augmentable<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional>
{


    /**
     * The enumeration built-in type represents values from a set of assigned names.
     *
     */
    public enum MultifunctionalStatus {
        /**
         * The multifunctional knob position is up. No toast is being made now.
         *
         */
        Up(1),
        
        /**
         * The multifunctional knob position is down. Toast is being made now.
         *
         */
        Down(2)
        ;
    
    
        int value;
        private static final java.util.Map<java.lang.Integer, MultifunctionalStatus> VALUE_MAP;
    
        static {
            final com.google.common.collect.ImmutableMap.Builder<java.lang.Integer, MultifunctionalStatus> b = com.google.common.collect.ImmutableMap.builder();
            for (MultifunctionalStatus enumItem : MultifunctionalStatus.values())
            {
                b.put(enumItem.value, enumItem);
            }
    
            VALUE_MAP = b.build();
        }
    
        private MultifunctionalStatus(int value) {
            this.value = value;
        }
    
        /**
         * @return integer value
         */
        public int getIntValue() {
            return value;
        }
    
        /**
         * @param valueArg
         * @return corresponding MultifunctionalStatus item
         */
        public static MultifunctionalStatus forValue(int valueArg) {
            return VALUE_MAP.get(valueArg);
        }
    }

    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.cachedReference(org.opendaylight.yangtools.yang.common.QName.create("http://inocybe.com/ns/multifunctional","2015-08-04","multifunctional"));

    /**
     * The name of the multifunctional's manufacturer. For instance, HP's 
     * multifunctional.
     *
     */
    DisplayString getMultifunctionalManufacturer();
    
    /**
     * The name of the multifunctional's model. For instance, Radiant Automatic.
     *
     */
    DisplayString getMultifunctionalModelNumber();
    
    /**
     * This variable indicates the current state of the multifunctional.
     *
     */
    MultifunctionalStatus getMultifunctionalStatus();

}

