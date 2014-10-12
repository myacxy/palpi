package net.myacxy.dashclock.twitch.io;

import android.app.ProgressDialog;
import android.content.Context;

import net.myacxy.dashclock.twitch.models.TwitchGame;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TwitchGameGetter extends JsonGetter {

    public TwitchGameGetter(Context context, ProgressDialog progressDialog) {
        super(context, progressDialog);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        return super.doInBackground(params);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        JSONArray top = null;
        try {
            top = jsonObject.getJSONArray("top");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<TwitchGame> games = parseJsonObject(top);

    }

    public void run(int limit, int offset) {
        String url = String.format("https://api.twitch.tv/kraken/games/top?limit=%d&offset=%d",
                limit, offset);
        executeOnExecutor(THREAD_POOL_EXECUTOR, url);
    }

    protected ArrayList<TwitchGame> parseJsonObject(JSONArray jsonGames)
    {
        // initialize
        ArrayList<TwitchGame> games = new ArrayList<TwitchGame>();

        for (int i = 0; i < jsonGames.length(); i++)
        {
            JSONObject game = null;
            // get channel from array
            try {
                game = jsonGames.getJSONObject(i).getJSONObject("game");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // parse json to TwitchGame
            TwitchGame tg = new TwitchGame(game);
            // add game to list
            games.add(tg);
        }
        return games;
    } // parseJsonObject
}