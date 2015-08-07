/*
 * Copyright(c) Inocybe Technologies and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.multifunctional.impl;

import org.junit.Test;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker;

import static org.mockito.Mockito.mock;

public class MultifunctionalProviderTest {
    @Test
    public void testOnSessionInitiated() {
        MultifunctionalProvider provider = new MultifunctionalProvider();

        // ensure no exceptions
        // currently this method is empty
        provider.onSessionInitiated(mock(BindingAwareBroker.ProviderContext.class));
    }

    @Test
    public void testClose() throws Exception {
        MultifunctionalProvider provider = new MultifunctionalProvider();

        // ensure no exceptions
        // currently this method is empty
        provider.close();
    }
}
