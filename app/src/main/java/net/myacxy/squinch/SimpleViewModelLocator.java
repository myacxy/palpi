package net.myacxy.squinch;

import android.content.Context;

import net.myacxy.squinch.helpers.DataHelper;
import net.myacxy.squinch.viewmodels.ChannelSelectionViewModel;
import net.myacxy.squinch.viewmodels.MainViewModel;
import net.myacxy.squinch.viewmodels.SettingsViewModel;

import java.util.HashMap;
import java.util.Map;

public class SimpleViewModelLocator {

    private static SimpleViewModelLocator INSTANCE;
    private final MainViewModel mMainViewModel;
    private final SettingsViewModel mSettingsViewModel;
    private final Map<String, ChannelSelectionViewModel> mChannelSelectionViewModels;
    private final DataHelper dataHelper;

    private SimpleViewModelLocator(Context context) {
        dataHelper = new DataHelper(context);

        mMainViewModel = new MainViewModel();
        mSettingsViewModel = new SettingsViewModel(dataHelper);
        mChannelSelectionViewModels = new HashMap<>();
    }

    public static SimpleViewModelLocator getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("initialize not called");
        }
        return INSTANCE;
    }

    public static void initialize(Context applicationContext) {
        INSTANCE = new SimpleViewModelLocator(applicationContext);
    }

    public MainViewModel getMainViewModel() {
        return mMainViewModel;
    }

    public SettingsViewModel getSettingsViewModel() {
        return mSettingsViewModel;
    }

    public ChannelSelectionViewModel getChannelSelectionViewModel(String userName) {
        if (mChannelSelectionViewModels.containsKey(userName)) {
            return mChannelSelectionViewModels.get(userName);
        }
        mChannelSelectionViewModels.clear();
        ChannelSelectionViewModel viewModel = new ChannelSelectionViewModel(dataHelper);
        mChannelSelectionViewModels.put(userName, viewModel);
        return viewModel;
    }
}