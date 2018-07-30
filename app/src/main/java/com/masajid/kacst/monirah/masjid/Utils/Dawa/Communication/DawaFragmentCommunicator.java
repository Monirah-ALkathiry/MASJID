package com.masajid.kacst.monirah.masjid.Utils.Dawa.Communication;

import com.masajid.kacst.monirah.masjid.Utils.Dawa.DawaLatLng;

import java.util.List;

/**
 * Created by Monirah on 7/24/2018.
 */

public interface DawaFragmentCommunicator {
    void passData(List<DawaLatLng> dawaLatLngs);
}
