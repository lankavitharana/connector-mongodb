/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.ballerinalang.data.mongodb.actions;

import org.ballerinalang.bre.Context;
import org.ballerinalang.connector.api.ConnectorFuture;
import org.ballerinalang.data.mongodb.Constants;
import org.ballerinalang.data.mongodb.MongoDBDataSource;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BConnector;
import org.ballerinalang.model.values.BInteger;
import org.ballerinalang.model.values.BJSON;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaAction;
import org.ballerinalang.natives.annotations.ReturnType;

/**
 * {@code Delete} action to delete documents in a collection.
 *
 * @since 0.95.0
 */
@BallerinaAction(
            packageName = "ballerina.data.mongodb",
            actionName = "delete",
            connectorName = Constants.CONNECTOR_NAME,
            args = {@Argument(name = "c", type = TypeKind.CONNECTOR),
                    @Argument(name = "collectionName", type = TypeKind.STRING),
                    @Argument(name = "filter", type = TypeKind.JSON),
                    @Argument(name = "multiple", type = TypeKind.BOOLEAN)
            },
            returnType = { @ReturnType(type = TypeKind.INT) }
        )
public class Delete extends AbstractMongoDBAction {

    @Override
    public ConnectorFuture execute(Context context) {
        BConnector bConnector = (BConnector) getRefArgument(context, 0);
        String collectionName = getStringArgument(context, 0);
        BJSON filter = (BJSON) getRefArgument(context, 1);
        Boolean isMultiple = getBooleanArgument(context, 0);
        MongoDBDataSource datasource = getDataSource(bConnector);
        long deletedCount = delete(datasource, collectionName, filter, isMultiple);
        context.getControlStackNew().getCurrentFrame().returnValues[0] = new BInteger(deletedCount);
        return getConnectorFuture();
    }
}
