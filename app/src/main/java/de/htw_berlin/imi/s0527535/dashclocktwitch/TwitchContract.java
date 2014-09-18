package de.htw_berlin.imi.s0527535.dashclocktwitch;

import android.provider.BaseColumns;

/**
 * @see <a href="http://goo.gl/vk1g8o">Android Developer</a>
 */
public class TwitchContract {

    /**
     * empty constructor to prevent accidental instantiation
     */
    public TwitchContract() {}

    public static abstract class ChannelEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_DISPLAY_NAME = "displayName";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_GAME = "game";
    }
}
