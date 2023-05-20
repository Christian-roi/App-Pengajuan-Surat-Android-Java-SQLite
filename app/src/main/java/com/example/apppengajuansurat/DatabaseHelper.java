package com.example.apppengajuansurat;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "surat.db";
    private static final int DB_VERSION = 1;
    private static final String PREF_USER_ID = "user_id";
    private SharedPreferences sharedPreferences;

    Date currentDate = new Date();
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        sharedPreferences = context.getSharedPreferences("user_id", Context.MODE_PRIVATE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE session(id interger PRIMARY KEY, login text)");
        db.execSQL("CREATE TABLE user(id INTEGER PRIMARY KEY AUTOINCREMENT, nama text, userid text, j_kelamin text, password text, role text)");
        db.execSQL("CREATE TABLE form_mhs_aktif(id INTEGER PRIMARY KEY AUTOINCREMENT, nama text, nim text, ttl text, " +
                "jurusan text, kelas text, alamat text, nama_ortu text, pekerjaan text, instansi text, alamat_ortu text, tgl_kirim text, status text)");
        db.execSQL("CREATE TABLE form_transkrip(id INTEGER PRIMARY KEY AUTOINCREMENT, nama_mhs text, nim text, ttl text, " +
                "semester text, jurusan text, kelas text, tgl_kirim text, status text)");
        db.execSQL("CREATE TABLE form_legalisir(id INTEGER PRIMARY KEY AUTOINCREMENT, nama_mhs text, nim text, ttl text, jurusan text, kelas text, tgl_kirim text, status text)");
        db.execSQL("INSERT INTO session(id, login) VALUES (1,'kosong')");
        db.execSQL("INSERT INTO user(id, nama, userid, j_kelamin, password, role) VALUES (1,'Admin Akademik', '1122334455','Pria', '12345', 'admin')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int db1, int db2) {
        db.execSQL("DROP TABLE IF EXISTS session");
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    //check session
    public Boolean checkSession(String sessionValues) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM session WHERE login = ?", new String[]{sessionValues});
        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    //upgrade session
    public Boolean upgradeSession(String sessionValues, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("login", sessionValues);
        long update = db.update("session", contentValues, "id="+id, null);
        if (update == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public String saveLoggedInUserId(String userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_USER_ID, userId);
        editor.apply();
        return userId;
    }

    public String getLoggedInUserId() {
        return sharedPreferences.getString(PREF_USER_ID, null);
    }

    //insert user
    public Boolean insertUser(String nama, String userid, String j_kelamin, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama", nama);
        contentValues.put("userid", userid);
        contentValues.put("j_kelamin", j_kelamin);
        contentValues.put("password", password);
        contentValues.put("role", role);
        long insert = db.insert("user", null, contentValues);
        if (insert == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    //Insert Form Mahasiswa Aktif
    public boolean insertFormMahasiswaAktif(String nama, String nim, String ttl, String jurusan, String kelas, String alamat, String nama_ortu, String pekerjaan, String instansi, String alamat_ortu){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(currentDate);
        contentValues.put("nama", nama);
        contentValues.put("nim", nim);
        contentValues.put("ttl", ttl);
        contentValues.put("jurusan", jurusan);
        contentValues.put("kelas", kelas);
        contentValues.put("alamat", alamat);
        contentValues.put("nama_ortu", nama_ortu);
        contentValues.put("pekerjaan", pekerjaan);
        contentValues.put("instansi", instansi);
        contentValues.put("alamat_ortu", alamat_ortu);
        contentValues.put("tgl_kirim", formattedDate);
        contentValues.put("status", "Dikirim");
        long insert = db.insert("form_mhs_aktif", null, contentValues);
        if(insert == -1){
            return false;
        }else{
            return  true;
        }
    }

    //Insert Form Transkrip
    public boolean insertFormTranskrip(String nama, String nim, String ttl, String semester,String jurusan, String kelas){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(currentDate);
        contentValues.put("nama_mhs", nama);
        contentValues.put("nim", nim);
        contentValues.put("ttl", ttl);
        contentValues.put("semester", semester);
        contentValues.put("jurusan", jurusan);
        contentValues.put("kelas", kelas);
        contentValues.put("tgl_kirim", formattedDate);
        contentValues.put("status", "Dikirim");
        long insert = db.insert("form_transkrip", null, contentValues);
        if(insert == -1){
            return false;
        }else{
            return  true;
        }
    }

    //Insert Form Legalisir
    public boolean insertFormLegalisir(String nama, String nim, String ttl, String jurusan, String kelas){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(currentDate);
        contentValues.put("nama_mhs", nama);
        contentValues.put("nim", nim);
        contentValues.put("ttl", ttl);
        contentValues.put("jurusan", jurusan);
        contentValues.put("kelas", kelas);
        contentValues.put("tgl_kirim", formattedDate);
        contentValues.put("status", "Dikirim");
        long insert = db.insert("form_legalisir", null, contentValues);
        if(insert == -1){
            return false;
        }else{
            return  true;
        }
    }
    public ArrayList<HashMap<String, String>> getFormMhsAktifByNim(String userid){
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM form_mhs_aktif WHERE nim = ?", new String[]{userid});
        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("nama", cursor.getString(1));
                map.put("nim", cursor.getString(2));
                map.put("ttl", cursor.getString(3));
                map.put("jurusan", cursor.getString(4));
                map.put("kelas", cursor.getString(5));
                map.put("alamat", cursor.getString(6));
                map.put("nama_ortu", cursor.getString(7));
                map.put("pekerjaan", cursor.getString(8));
                map.put("instansi", cursor.getString(9));
                map.put("alamat_ortu", cursor.getString(10));
                map.put("tgl_kirim", cursor.getString(11));
                map.put("status", cursor.getString(12));
                list.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<HashMap<String, String>> getFormLegalisirByNim(String userid){
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM form_legalisir WHERE nim = ?", new String[]{userid});
        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("nama_mhs", cursor.getString(1));
                map.put("nim", cursor.getString(2));
                map.put("ttl", cursor.getString(3));
                map.put("jurusan", cursor.getString(4));
                map.put("kelas", cursor.getString(5));
                map.put("tgl_kirim", cursor.getString(6));
                map.put("status", cursor.getString(7));
                list.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<HashMap<String, String>> getFormTranskripByNim(String userid){
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM form_transkrip WHERE nim = ?", new String[]{userid});
        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("nama_mhs", cursor.getString(1));
                map.put("nim", cursor.getString(2));
                map.put("ttl", cursor.getString(3));
                map.put("semester", cursor.getString(4));
                map.put("jurusan", cursor.getString(5));
                map.put("kelas", cursor.getString(6));
                map.put("tgl_kirim", cursor.getString(7));
                map.put("status", cursor.getString(8));
                list.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<HashMap<String, String>> getAllFormMhsAktif(){
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM form_mhs_aktif WHERE status != ?", new String[]{"Dibatalkan"});
        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("nama", cursor.getString(1));
                map.put("nim", cursor.getString(2));
                map.put("ttl", cursor.getString(3));
                map.put("jurusan", cursor.getString(4));
                map.put("kelas", cursor.getString(5));
                map.put("alamat", cursor.getString(6));
                map.put("nama_ortu", cursor.getString(7));
                map.put("pekerjaan", cursor.getString(8));
                map.put("instansi", cursor.getString(9));
                map.put("alamat_ortu", cursor.getString(10));
                map.put("tgl_kirim", cursor.getString(11));
                map.put("status", cursor.getString(12));
                list.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<HashMap<String, String>> getAllFormLegalisir(){
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM form_legalisir WHERE status != ?", new String[]{"Dibatalkan"});
        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("nama_mhs", cursor.getString(1));
                map.put("nim", cursor.getString(2));
                map.put("ttl", cursor.getString(3));
                map.put("jurusan", cursor.getString(4));
                map.put("kelas", cursor.getString(5));
                map.put("tgl_kirim", cursor.getString(6));
                map.put("status", cursor.getString(7));
                list.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<HashMap<String, String>> getAllFormTranskrip(){
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM form_transkrip WHERE status != ?", new String[]{"Dibatalkan"});
        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("nama_mhs", cursor.getString(1));
                map.put("nim", cursor.getString(2));
                map.put("ttl", cursor.getString(3));
                map.put("semester", cursor.getString(4));
                map.put("jurusan", cursor.getString(5));
                map.put("kelas", cursor.getString(6));
                map.put("tgl_kirim", cursor.getString(7));
                map.put("status", cursor.getString(8));
                list.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    //check login
    public Boolean checkLogin(String userid, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE userid = ? AND password = ?", new String[]{userid, password});
        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    //Cek Role
    public String checkRole(String userid){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT role FROM user WHERE userid = ?", new String[]{userid});
        String role = null;
        if(cursor.moveToFirst()){
            role = cursor.getString(cursor.getColumnIndexOrThrow("role"));
        }
        cursor.close();
        return role;
    }

    //Cek NIM
    public boolean isUserIdExists(String userid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM user WHERE userid = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userid});

        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }

        return count > 0;
    }

    //Get User data
    public UserData getUserData (String userid){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE userid = ?", new String[]{userid});
        UserData userData = null;
        if(cursor.moveToFirst()){
            String valueNama = cursor.getString(cursor.getColumnIndexOrThrow("nama"));
            String valueUserid = cursor.getString(cursor.getColumnIndexOrThrow("userid"));
            String valueJkelamin = cursor.getString(cursor.getColumnIndexOrThrow("j_kelamin"));
            userData = new UserData(valueNama, valueUserid, valueJkelamin);
        }
        cursor.close();
        return userData;
    }

    public int getRowCount(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return count;
    }

    public int getFormMhsAktifCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM form_mhs_aktif WHERE status = 'Dikirim'";
        Cursor cursor = db.rawQuery(query,null);
        int count = 0;

        if(cursor.moveToFirst()){
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public int getFormTranskripCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM form_transkrip WHERE status = 'Dikirim'";
        Cursor cursor = db.rawQuery(query,null);
        int count = 0;

        if(cursor.moveToFirst()){
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public int getFormLegalisirCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM form_legalisir WHERE status = 'Dikirim'";
        Cursor cursor = db.rawQuery(query,null);
        int count = 0;

        if(cursor.moveToFirst()){
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public int getCountFormSelesai(String nim, String status) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT COUNT(*) AS total FROM (" +
                "SELECT nim, status FROM form_mhs_aktif UNION ALL " +
                "SELECT nim, status FROM form_transkrip UNION ALL " +
                "SELECT nim, status FROM form_legalisir" +
                ") WHERE nim = ? AND status = ?";

        Cursor cursor = db.rawQuery(query, new String[]{nim, status});
        int totalCount = 0;

        if (cursor.moveToFirst()) {
            totalCount = cursor.getInt(cursor.getColumnIndexOrThrow("total"));
        }

        cursor.close();
        db.close();

        return totalCount;
    }

    public class UserData{
        private String valueNama;
        private String valueUserid;
        private String valueJkelamin;

        public UserData (String valueNama, String valueUserid, String valueJkelamin){
            this.valueNama = valueNama;
            this.valueUserid = valueUserid;
            this.valueJkelamin = valueJkelamin;
        }

        public String getValueNama(){
            return valueNama;
        }
        public String getValueUserid(){
            return valueUserid;
        }
        public String getValueJkelamin(){
            return valueJkelamin;
        }
    }

    public boolean areValuesNotEmpty(String... values) {
        for (String value : values) {
            if (value == null || value.trim().isEmpty()) {
                return false; // Value is empty
            }
        }
        return true; // All values are not empty
    }
}
