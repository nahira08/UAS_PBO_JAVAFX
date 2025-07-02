package com.perpus.views.auth;

import com.perpus.model.Anggota;
import com.perpus.model.DataStore;

/*
 ============================================================================
 DOKUMENTASI KODE - UAS PBO SISTEM PERPUSTAKAAN
 ----------------------------------------------------------------------------
 Nama Anggota Kelompok:
 - Nur Aini (202410370110381)
 - Alifia Nadia Ruksana (202410370110334)
 - Ayshea Marvella Pasha (202410370110379)
 - Jenita Oktaviana Ramadhani (202410370110338)
 ----------------------------------------------------------------------------
 File        : AuthService.java
 Deskripsi   : Class utilitas untuk otentikasi login anggota berdasarkan ID dan nama.
 ============================================================================
*/

public class AuthService {

    /**
     * FITUR   : Autentikasi Anggota
     * FUNGSI  : Mengecek apakah ID dan nama pengguna cocok dengan data anggota yang terdaftar.
     *
     * @param id        ID anggota (case-insensitive)
     * @param username  Nama pengguna yang dimasukkan saat login (case-insensitive)
     * @return          True jika ditemukan kecocokan, false jika tidak
     */
    public static boolean login(String id, String username) {
        for (Anggota anggota : DataStore.daftarAnggota) {
            if (anggota.getId().equalsIgnoreCase(id) &&
                anggota.getNama().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }
}
