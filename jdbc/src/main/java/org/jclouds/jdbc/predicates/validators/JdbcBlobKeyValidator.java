/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.jdbc.predicates.validators;

import com.google.inject.Singleton;
import org.jclouds.predicates.Validator;

/**
 * Validates name for jdbc container blob keys implementation
 *
 * @see org.jclouds.rest.InputParamValidator
 * @see org.jclouds.predicates.Validator
 */
@Singleton
public class JdbcBlobKeyValidator extends Validator<String> {

    @Override
    public void validate(String name) throws IllegalArgumentException {
        //blob key cannot be null or empty
        if (name == null || name.length() < 1)
            throw new IllegalArgumentException("Blob key can't be null or empty");
    }

}
