package com.rk.common.utils.wheel;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;


import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rk.common.R;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * CodeFatCat
 */

public class PickCityUtil {

    private static ArrayList<String> options1Items = new ArrayList<>();//一级
    private static ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//二级
    private static ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//三级

    private static ArrayList<String> areaId1 = new ArrayList<>();//一级
    private static ArrayList<ArrayList<String>> areaId2 = new ArrayList<>();//二级
    private static ArrayList<ArrayList<ArrayList<String>>> areaId3 = new ArrayList<>();//三级

    private static ArrayList<String> year = new ArrayList<>();//年
    private static ArrayList<String> month = new ArrayList<>();//月
    private static ArrayList<String> day = new ArrayList<>();//日

    private static String mJsonObj;
    private static Gson gson;

    /**
     * 获取城市信息字符串
     *
     * @param context
     * @return
     */
    public static String getCityJson(Context context) {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = context.getAssets().open("json/city.json");
            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "utf-8"));
            }
            is.close();
            mJsonObj = sb.toString();
            return mJsonObj;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void initPickView(Context context) {

        try {
            InputStream inputStream = context.getAssets().open("json/city.json");
            //  String cityJson = getCityJson(context);
            String cityJson = getString(inputStream);
            if (gson == null) {
                gson = new Gson();
            }
            if (cityJson == null) {
                return;
            }
            List<CityDataModel> dataModelList = gson.fromJson(cityJson, new TypeToken<List<CityDataModel>>() {
            }.getType());

            if (dataModelList != null && !dataModelList.isEmpty()) {
                options1Items.clear();
                options2Items.clear();
                options3Items.clear();
                areaId1.clear();
                areaId2.clear();
                areaId3.clear();
                for (int i = 0; i < dataModelList.size(); i++) {
                    options1Items.add(dataModelList.get(i).areaName);//省
                    areaId1.add(dataModelList.get(i).areaId);
                    List<CityDataModel.CitiesBean> citiesBeanList = dataModelList.get(i).cities;//市

                    ArrayList<String> options2Items_item = new ArrayList<>();
                    ArrayList<ArrayList<String>> options3Items_item = new ArrayList<>();
                    //区域码
                    ArrayList<String> areaId2Items_item = new ArrayList<>();
                    ArrayList<ArrayList<String>> areaId3Items_item = new ArrayList<>();

                    for (int j = 0; j < citiesBeanList.size(); j++) {//市
                        options2Items_item.add(citiesBeanList.get(j).areaName);
                        areaId2Items_item.add(citiesBeanList.get(j).areaId);

                        List<CityDataModel.CitiesBean.CountiesBean> countiesBeanList = citiesBeanList.get(j).counties;//区

                        ArrayList<String> options3Items_item_item = new ArrayList<>();
                        ArrayList<String> areaId3Items_item_item = new ArrayList<>();
                        for (CityDataModel.CitiesBean.CountiesBean countiesBean : countiesBeanList) {//区
                            options3Items_item_item.add(countiesBean.areaName);
                            areaId3Items_item_item.add(countiesBean.areaId);
                        }


                        options3Items_item.add(options3Items_item_item);
                        areaId3Items_item.add(areaId3Items_item_item);

                    }
                    options2Items.add(options2Items_item);
                    options3Items.add(options3Items_item);
                    //区域编码
                    areaId2.add(areaId2Items_item);
                    areaId3.add(areaId3Items_item);

                }
            }
        } catch (Exception e) {
            Log.e("e:", "" + e.getMessage());
        }

    }

    /**
     * 城市选择
     *
     * @param
     * @param context
     */
    public static void showCityPickView(Context context, final ChooseCityListener listener) {
        Log.i("wwb", "showCityPickView: ");
        if (options1Items.isEmpty() || options2Items.isEmpty() || options3Items.isEmpty()) {
            return;
        }
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String p = options1Items.get(options1);
                String c = options2Items.get(options1).get(options2);
                String a = options3Items.get(options1).get(options2).get(options3);
                listener.chooseCity(p + "_" + c + "_" + a);
//                if(p.equals(c)){
//                    listener.chooseCity(c+"_"+a);
//                }else {
//                    listener.chooseCity(p+"_"+c+"_"+a);
//                }
            }
        })
                .setTitleText("Select City")
                .setCancelColor(R.color.color999999)
                .setSubmitColor(R.color.color999999)
                .setContentTextSize(16)
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);

            pvOptions.show();




    }


    /**
     * 日期选择
     *
     * @param
     * @param context
     */
    public static void showTimePickView(Context context, final ChooseCityListener listener) {

        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Log.i("wwb", "onTimeSelect: " + date.toString());

                listener.chooseCity(getTime(date));
            }
        })
                .setTitleText("")
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setCancelColor(context.getResources().getColor(R.color.color999999))
                .setSubmitColor(context.getResources().getColor(R.color.color333333))
                .setCancelText("取消")
                .setSubmitText("确定")
                .setLabel("", "", "", "", "", "")
                .build();
        pvTime.setDate(Calendar.getInstance());
        //显示
        pvTime.show();
    }
    /**
     * 日期选择
     *
     * @param
     * @param context
     */
    public static void showTimePickViewTwo(Context context, final ChooseCityListenerDate listener) {

        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Log.i("wwb", "onTimeSelect: " + date.toString());

                listener.chooseDate(date);
            }
        })
                .setTitleText("")
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setCancelColor(context.getResources().getColor(R.color.color999999))
                .setSubmitColor(context.getResources().getColor(R.color.color333333))
                .setCancelText("取消")
                .setSubmitText("确定")
                .setLabel("", "", "", "", "", "")
                .build();
        pvTime.setDate(Calendar.getInstance());
        //显示
        pvTime.show();
    }
    /**
     * 单列表
     *
     * @param
     * @param context
     */
    public static void showSinglePickView(Context context, final List<String> list, String title, final ChoosePositionListener listener) {

        if (list.isEmpty()) {
            return;
        }

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                listener.choosePosition(options1, list.get(options1));
            }
        })
                .setTitleText(title)
                .setTitleBgColor(Color.WHITE)
                .setCancelColor(context.getResources().getColor(R.color.color999999))
//                .setCancelText(context.getResources().getString(R.string.cancel))
//                .setSubmitText(context.getResources().getString(R.string.next))
//                .setSubmitColor(context.getResources().getColor(R.color.common_color))
                .build();


        pvOptions.setPicker(list);
        pvOptions.show();
    }

    /**
     * 双列表
     *
     * @param
     * @param context
     */
    public static void showDoublePickView(Context context, final List<String> list1, final List<List<String>> list2, String title, final ChooseDPositionListener listener) {

        if (list1.isEmpty() || list2.isEmpty()) {
            return;
        }

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                listener.choosePosition(options1, options2, list1.get(options1) + "_" + list2.get(options1).get(options2));
            }
        })
                .setTitleText(title)
                .build();
        pvOptions.setPicker(list1, list2);
        pvOptions.show();


    }

    /**
     * @param
     * @param context
     */
    public static void showCityPickView(Context context, final ChooseCityListener listener, final ChooseCityAreaIdListener areaIdListener) {

        if (options1Items.isEmpty() || options2Items.isEmpty() || options3Items.isEmpty()) {
            return;
        }
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String p = options1Items.get(options1);
                String c = options2Items.get(options1).get(options2);
                String a = options3Items.get(options1).get(options2).get(options3);
                if (p.equals(c)) {
                    listener.chooseCity(c + "_" + a);
                } else {
                    listener.chooseCity(p + "_" + c + "_" + a);
                }
                String aa = areaId3.get(options1).get(options2).get(options3);
                areaIdListener.chooseAreaId(aa);
            }
        })
                .setTitleText("Select City")
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
    }


    public interface ChooseCityListener {
        void chooseCity(String s);
    }

    public interface ChooseCityListenerDate{
        void chooseDate(Date date);
    }

    public interface ChoosePositionListener {
        void choosePosition(int position, String s);
    }

    public interface ChooseDPositionListener {
        void choosePosition(int position1, int position2, String s);
    }

    public interface ChooseCityAreaIdListener {
        void chooseAreaId(String s);
    }


    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return format.format(date);
    }
}
