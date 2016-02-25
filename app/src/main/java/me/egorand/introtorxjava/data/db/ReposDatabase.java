/*
 * Copyright 2016 Egor Andreevici
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.egorand.introtorxjava.data.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * @author Egor
 */
@Database(name = ReposDatabase.DATABASE_NAME, version = ReposDatabase.DATABASE_VERSION)
public class ReposDatabase {

    public static final String DATABASE_NAME = "Repos";
    public static final int DATABASE_VERSION = 1;
}
