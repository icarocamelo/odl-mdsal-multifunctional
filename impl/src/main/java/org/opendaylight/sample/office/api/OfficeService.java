/*
 * Copyright (c) 2015 Inocybe and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.sample.office.api;

import java.util.concurrent.Future;

import org.opendaylight.yangtools.yang.common.RpcResult;

public interface OfficeService {
    Future<RpcResult<Void>> createPrint( PrintType printType, long numberOfPages, int printDoneness );
}
