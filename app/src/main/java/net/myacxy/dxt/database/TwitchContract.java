/**
 * Copyright (c) 2014, Johannes Hoffmann
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 * and the following disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.myacxy.dxt.database;

import android.provider.BaseColumns;

/**
 * @see <a href="http://goo.gl/vk1g8o">Android Developer</a>
 */
public final class TwitchContract {

    /**
     * empty constructor to prevent accidental instantiation
     */
    public TwitchContract() {}

    /**
     * Inner class that defines the table contents
     */
    public static abstract class ChannelEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "channels";
        public static final String COLUMN_NAME_ENTRY_ID = "entryId";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DISPLAY_NAME = "displayName";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_GAME_ID = "gameId";
        public static final String COLUMN_NAME_ONLINE = "online";
        public static final String COLUMN_NAME_SELECTED = "selected";
        public static final String COLUMN_NAME_VIEWERS = "viewers";
        public static final String COLUMN_NAME_FOLLOWERS = "followers";
        public static final String COLUMN_NAME_UPDATED_AT = "updatedAt";
        public static final String COLUMN_NAME_LOGO = "logo";
        public static final String COLUMN_NAME_PREVIEW = "preview";
    }

    public static abstract class GameEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "games";
        public static final String COLUMN_NAME_ENTRY_ID = "entryId";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_ABBREVIATION = "abbreviation";
        public static final String COLUMN_NAME_CHANNELS = "channels";
        public static final String COLUMN_NAME_VIEWERS = "viewers";
        public static final String COLUMN_NAME_LOGO = "logo";
    }
}