package org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804;
import com.google.common.collect.Range;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import java.math.BigInteger;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional.MultifunctionalStatus;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.concepts.Builder;
import java.util.ArrayList;
import java.util.List;
import org.opendaylight.yangtools.yang.binding.Augmentation;


/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional} instances.
 *
 * @see org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional
 *
 */
public class MultifunctionalBuilder implements Builder <org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional> {

    private DisplayString _multifunctionalManufacturer;
    private static void check_multifunctionalManufacturerLength(final String value) {
        final int length = value.length();
        if (length >= 0 && length <= 255) {
            return;
        }
        throw new IllegalArgumentException(String.format("Invalid length: %s, expected: [[0‥255]].", value));
    }
    private DisplayString _multifunctionalModelNumber;
    private static void check_multifunctionalModelNumberLength(final String value) {
        final int length = value.length();
        if (length >= 0 && length <= 255) {
            return;
        }
        throw new IllegalArgumentException(String.format("Invalid length: %s, expected: [[0‥255]].", value));
    }
    private MultifunctionalStatus _multifunctionalStatus;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional>>, Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional>> augmentation = Collections.emptyMap();

    public MultifunctionalBuilder() {
    }

    public MultifunctionalBuilder(Multifunctional base) {
        this._multifunctionalManufacturer = base.getMultifunctionalManufacturer();
        this._multifunctionalModelNumber = base.getMultifunctionalModelNumber();
        this._multifunctionalStatus = base.getMultifunctionalStatus();
        if (base instanceof MultifunctionalImpl) {
            MultifunctionalImpl impl = (MultifunctionalImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }


    public DisplayString getMultifunctionalManufacturer() {
        return _multifunctionalManufacturer;
    }
    
    public DisplayString getMultifunctionalModelNumber() {
        return _multifunctionalModelNumber;
    }
    
    public MultifunctionalStatus getMultifunctionalStatus() {
        return _multifunctionalStatus;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

    public MultifunctionalBuilder setMultifunctionalManufacturer(DisplayString value) {
        if (value != null) {
            check_multifunctionalManufacturerLength(value.getValue());
        }
        this._multifunctionalManufacturer = value;
        return this;
    }
    /**
     * @deprecated This method is slated for removal in a future release. See BUG-1485 for details.
     */
    @Deprecated
    public static List<Range<BigInteger>> _multifunctionalManufacturer_length() {
        List<Range<BigInteger>> ret = new ArrayList<>(1);
        ret.add(Range.closed(BigInteger.ZERO, BigInteger.valueOf(255L)));
        return ret;
    }
    
    public MultifunctionalBuilder setMultifunctionalModelNumber(DisplayString value) {
        if (value != null) {
            check_multifunctionalModelNumberLength(value.getValue());
        }
        this._multifunctionalModelNumber = value;
        return this;
    }
    /**
     * @deprecated This method is slated for removal in a future release. See BUG-1485 for details.
     */
    @Deprecated
    public static List<Range<BigInteger>> _multifunctionalModelNumber_length() {
        List<Range<BigInteger>> ret = new ArrayList<>(1);
        ret.add(Range.closed(BigInteger.ZERO, BigInteger.valueOf(255L)));
        return ret;
    }
    
    public MultifunctionalBuilder setMultifunctionalStatus(MultifunctionalStatus value) {
        this._multifunctionalStatus = value;
        return this;
    }
    
    public MultifunctionalBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional> augmentation) {
        if (augmentation == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }
    
    public MultifunctionalBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    public Multifunctional build() {
        return new MultifunctionalImpl(this);
    }

    private static final class MultifunctionalImpl implements Multifunctional {

        public java.lang.Class<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional.class;
        }

        private final DisplayString _multifunctionalManufacturer;
        private final DisplayString _multifunctionalModelNumber;
        private final MultifunctionalStatus _multifunctionalStatus;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional>>, Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional>> augmentation = Collections.emptyMap();

        private MultifunctionalImpl(MultifunctionalBuilder base) {
            this._multifunctionalManufacturer = base.getMultifunctionalManufacturer();
            this._multifunctionalModelNumber = base.getMultifunctionalModelNumber();
            this._multifunctionalStatus = base.getMultifunctionalStatus();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional>>, Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional>>, Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public DisplayString getMultifunctionalManufacturer() {
            return _multifunctionalManufacturer;
        }
        
        @Override
        public DisplayString getMultifunctionalModelNumber() {
            return _multifunctionalModelNumber;
        }
        
        @Override
        public MultifunctionalStatus getMultifunctionalStatus() {
            return _multifunctionalStatus;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + ((_multifunctionalManufacturer == null) ? 0 : _multifunctionalManufacturer.hashCode());
            result = prime * result + ((_multifunctionalModelNumber == null) ? 0 : _multifunctionalModelNumber.hashCode());
            result = prime * result + ((_multifunctionalStatus == null) ? 0 : _multifunctionalStatus.hashCode());
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
            if (!org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional other = (org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional)obj;
            if (_multifunctionalManufacturer == null) {
                if (other.getMultifunctionalManufacturer() != null) {
                    return false;
                }
            } else if(!_multifunctionalManufacturer.equals(other.getMultifunctionalManufacturer())) {
                return false;
            }
            if (_multifunctionalModelNumber == null) {
                if (other.getMultifunctionalModelNumber() != null) {
                    return false;
                }
            } else if(!_multifunctionalModelNumber.equals(other.getMultifunctionalModelNumber())) {
                return false;
            }
            if (_multifunctionalStatus == null) {
                if (other.getMultifunctionalStatus() != null) {
                    return false;
                }
            } else if(!_multifunctionalStatus.equals(other.getMultifunctionalStatus())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                MultifunctionalImpl otherImpl = (MultifunctionalImpl) obj;
                if (augmentation == null) {
                    if (otherImpl.augmentation != null) {
                        return false;
                    }
                } else if(!augmentation.equals(otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional>>, Augmentation<org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional>> e : augmentation.entrySet()) {
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
            java.lang.StringBuilder builder = new java.lang.StringBuilder ("Multifunctional [");
            boolean first = true;
        
            if (_multifunctionalManufacturer != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_multifunctionalManufacturer=");
                builder.append(_multifunctionalManufacturer);
             }
            if (_multifunctionalModelNumber != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_multifunctionalModelNumber=");
                builder.append(_multifunctionalModelNumber);
             }
            if (_multifunctionalStatus != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_multifunctionalStatus=");
                builder.append(_multifunctionalStatus);
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
