package org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804;
import com.google.common.collect.Range;
import java.util.Collections;
import java.util.Map;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import java.util.List;
import java.math.BigInteger;
import org.opendaylight.yangtools.yang.binding.Augmentation;


/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput} instances.
 *
 * @see org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput
 *
 */
public class PrintInputBuilder implements Builder <org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput> {

    private java.lang.Long _numberOfPages;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput>>, Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput>> augmentation = Collections.emptyMap();

    public PrintInputBuilder() {
    }

    public PrintInputBuilder(PrintInput base) {
        this._numberOfPages = base.getNumberOfPages();
        if (base instanceof PrintInputImpl) {
            PrintInputImpl impl = (PrintInputImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }


    public java.lang.Long getNumberOfPages() {
        return _numberOfPages;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

    private static void checkNumberOfPagesRange(final long value) {
        if (value >= 1L && value <= 10L) {
            return;
        }
        throw new IllegalArgumentException(String.format("Invalid range: %s, expected: [[1‥10]].", value));
    }
    
    public PrintInputBuilder setNumberOfPages(java.lang.Long value) {
        if (value != null) {
            checkNumberOfPagesRange(value);
        }
        this._numberOfPages = value;
        return this;
    }
    /**
     * @deprecated This method is slated for removal in a future release. See BUG-1485 for details.
     */
    @Deprecated
    public static List<Range<BigInteger>> _numberOfPages_range() {
        final List<Range<BigInteger>> ret = new java.util.ArrayList<>(1);
        ret.add(Range.closed(BigInteger.ONE, BigInteger.TEN));
        return ret;
    }
    
    public PrintInputBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput> augmentation) {
        if (augmentation == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }
    
    public PrintInputBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    public PrintInput build() {
        return new PrintInputImpl(this);
    }

    private static final class PrintInputImpl implements PrintInput {

        public java.lang.Class<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput.class;
        }

        private final java.lang.Long _numberOfPages;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput>>, Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput>> augmentation = Collections.emptyMap();

        private PrintInputImpl(PrintInputBuilder base) {
            this._numberOfPages = base.getNumberOfPages();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput>>, Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput>>, Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public java.lang.Long getNumberOfPages() {
            return _numberOfPages;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput>> E getAugmentation(java.lang.Class<E> augmentationType) {
            if (augmentationType == null) {
                throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
            }
            return (E) augmentation.get(augmentationType);
        }

        private int hash = 0;
        private volatile boolean hashValid = false;
        
        @Override
        public int hashCode() {
            if (hashValid) {
                return hash;
            }
        
            final int prime = 31;
            int result = 1;
            result = prime * result + ((_numberOfPages == null) ? 0 : _numberOfPages.hashCode());
            result = prime * result + ((augmentation == null) ? 0 : augmentation.hashCode());
        
            hash = result;
            hashValid = true;
            return result;
        }

        @Override
        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DataObject)) {
                return false;
            }
            if (!org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput other = (org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput)obj;
            if (_numberOfPages == null) {
                if (other.getNumberOfPages() != null) {
                    return false;
                }
            } else if(!_numberOfPages.equals(other.getNumberOfPages())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                PrintInputImpl otherImpl = (PrintInputImpl) obj;
                if (augmentation == null) {
                    if (otherImpl.augmentation != null) {
                        return false;
                    }
                } else if(!augmentation.equals(otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput>>, Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput>> e : augmentation.entrySet()) {
                    if (!e.getValue().equals(other.getAugmentation(e.getKey()))) {
                        return false;
                    }
                }
                // .. and give the other one the chance to do the same
                if (!obj.equals(this)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public java.lang.String toString() {
            java.lang.StringBuilder builder = new java.lang.StringBuilder ("PrintInput [");
            boolean first = true;
        
            if (_numberOfPages != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_numberOfPages=");
                builder.append(_numberOfPages);
             }
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }
            builder.append("augmentation=");
            builder.append(augmentation.values());
            return builder.append(']').toString();
        }
    }

}
