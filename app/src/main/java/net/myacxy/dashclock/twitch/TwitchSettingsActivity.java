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

package net.myacxy.dashclock.twitch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;

import net.myacxy.dashclock.twitch.io.TwitchDbHelper;
import net.myacxy.dashclock.twitch.ui.FollowingSelectionDialog;
import net.myacxy.dashclock.twitch.ui.IntervalPreference;
import net.myacxy.dashclock.twitch.ui.UserNameDialog;

import java.util.HashSet;
import java.util.Set;

/**
 * Source=DashClock Example Extension Settings
 */
public class TwitchSettingsActivity extends BaseSettingsActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setIcon(R.drawable.twitch_purple);
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        bindIntervalPreference();
        bindSelectionPreference();
        bindUserNamePreference();

        CheckBoxPreference checkedTextView = (CheckBoxPreference) findPreference("pref_custom_visibility");
        checkedTextView.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new TwitchDbHelper(getApplicationContext()).updatePublishedData();
                return true;
            }
        });

        Preference donate = findPreference("pref_donate");
        donate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                String url = "https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=FBWHP6A4GDM9Q";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }
        });

        CheckBoxPreference hideNeutral = (CheckBoxPreference) findPreference("pref_dialog_hide_neutral");
        hideNeutral.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                boolean isChecked = ((CheckBoxPreference) preference).isChecked();
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sp.edit().putBoolean(TwitchExtension.PREF_DIALOG_HIDE_NEUTRAL_BUTTON, isChecked).apply();
                return true;
            }
        });

        CheckBoxPreference hideEmpty = (CheckBoxPreference) findPreference("pref_hide_empty");
        hideEmpty.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                boolean isChecked = ((CheckBoxPreference) preference).isChecked();
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sp.edit().putBoolean(TwitchExtension.PREF_HIDE_EMPTY, isChecked).apply();
                new TwitchDbHelper(getApplicationContext()).updatePublishedData();
                return true;
            }
        });
    }

    private void bindIntervalPreference() {
        IntervalPreference preference = (IntervalPreference) findPreference("pref_update_interval");

        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(newValue + " minutes");
                return true;
            }
        });

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int currentValue = sp.getInt(TwitchExtension.PREF_UPDATE_INTERVAL, 5);
        preference.getOnPreferenceChangeListener().onPreferenceChange(preference, currentValue);
    }

    private void bindSelectionPreference() {
        FollowingSelectionDialog preference =
                (FollowingSelectionDialog) findPreference("pref_following_selection");
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Set<String> allChannels =  sp.getStringSet(
                        TwitchExtension.PREF_ALL_FOLLOWED_CHANNELS, new HashSet<String>());
                int totalCount = allChannels.size();
                Set<String> selectedChannels = (Set<String>) newValue;
                int selectedCount = selectedChannels.size();
                String summary = String.format("%d out of %d channels selected", selectedCount, totalCount);
                        preference.setSummary(summary);
                return true;
            }
        });

        Set<String> currentValue = sp.getStringSet(
                TwitchExtension.PREF_SELECTED_FOLLOWED_CHANNELS, new HashSet<String>());
        preference.getOnPreferenceChangeListener().onPreferenceChange(preference, currentValue);
    }

    private void bindUserNamePreference() {
        UserNameDialog preference = (UserNameDialog) findPreference("pref_user_name");

        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(newValue.toString());

                FollowingSelectionDialog selectionPreference =
                        (FollowingSelectionDialog) findPreference("pref_following_selection");

                selectionPreference.getOnPreferenceChangeListener().onPreferenceChange(
                        selectionPreference, new HashSet<String>());

                return true;
            }
        });

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String currentValue = sp.getString(TwitchExtension.PREF_USER_NAME, "test_user1");
        preference.getOnPreferenceChangeListener().onPreferenceChange(preference, currentValue);
    }
}
