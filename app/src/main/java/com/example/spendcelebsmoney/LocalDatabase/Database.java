/*
 * *
 *  * Copyright (C) 2017 Ryan Kay Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.example.spendcelebsmoney.LocalDatabase;


import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@androidx.room.Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {


    public abstract UserDAO userDAO();

    private static volatile Database database;

    static Database fetchDB(final Context context) {
        if (database == null) {
            synchronized (Database.class) {
                if (database == null) {
                    database = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, "userDB")
                            .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return database;
    }


}
