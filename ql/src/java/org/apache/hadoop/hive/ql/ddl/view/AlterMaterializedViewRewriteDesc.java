/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.ql.ddl.view;

import org.apache.hadoop.hive.ql.ddl.DDLTask2;
import org.apache.hadoop.hive.ql.plan.DDLDesc.DDLDescWithWriteId;
import org.apache.hadoop.hive.ql.plan.Explain;
import org.apache.hadoop.hive.ql.plan.Explain.Level;

/**
 * DDL task description for the ALTER MATERIALIZED VIEW commands.
 */
@Explain(displayName = "Alter Materialized View", explainLevels = { Level.USER, Level.DEFAULT, Level.EXTENDED })
public class AlterMaterializedViewRewriteDesc extends AlterMaterializedViewDesc implements DDLDescWithWriteId {
  private static final long serialVersionUID = 1L;

  static {
    DDLTask2.registerOperation(AlterMaterializedViewRewriteDesc.class, AlterMaterializedViewRewriteOperation.class);
  }

  private final String fqMaterializedViewName;
  private final boolean rewriteEnable;

  public AlterMaterializedViewRewriteDesc(String fqMaterializedViewName, boolean rewriteEnable) {
    super(AlterMaterializedViewTypes.UPDATE_REWRITE_FLAG);
    this.fqMaterializedViewName = fqMaterializedViewName;
    this.rewriteEnable = rewriteEnable;
  }

  @Explain(displayName = "name", explainLevels = { Level.USER, Level.DEFAULT, Level.EXTENDED })
  public String getMaterializedViewName() {
    return fqMaterializedViewName;
  }

  public boolean isRewriteEnable() {
    return rewriteEnable;
  }

  @Override
  public void setWriteId(long writeId) {
    // We don't actually need the write id, but by implementing DDLDescWithWriteId it ensures that it is allocated
  }

  @Override
  public String getFullTableName() {
    return fqMaterializedViewName;
  }

  @Override
  public boolean mayNeedWriteId() {
    return true; // Verified when this is set as DDL Desc for ACID.
  }
}
