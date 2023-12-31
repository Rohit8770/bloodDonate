package com.example.blooddonatehub.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonatehub.Adapter.AllPersonRelationAdapter;
import com.example.blooddonatehub.Adapter.RelationAdapter;
import com.example.blooddonatehub.BloodHomeActivity;
import com.example.blooddonatehub.R;
import com.example.blooddonatehub.Response.BloodDonateListResponse;
import com.example.blooddonatehub.Response.BloodRequestListResponse;
import com.example.blooddonatehub.Response.EditStatusListResponse;
import com.example.blooddonatehub.Utils.Tools;
import com.example.blooddonatehub.Utils.VariableBag;
import com.example.blooddonatehub.network.RestClient;
import com.example.blooddonatehub.network.Restcall;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class FirstDonateFragment extends Fragment {
    //AllPersonRelationAdapter allPersonRelationAdapter;
   AllPersonRelationAdapter allPersonRelationAdapter;
   SwipeRefreshLayout refresh;
    RecyclerView rcvBloodType;
    EditText etSearch;
    ImageView tvNoData;
    TextView tvNoDataFound;
    Restcall restcall;
    String bg;
    Tools tools;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first_donate, container, false);

        rcvBloodType = v.findViewById(R.id.rcvBloodType);
        etSearch = v.findViewById(R.id.etSearch);
        tvNoData = v.findViewById(R.id.tvNoData);
        tvNoDataFound = v.findViewById(R.id.tvNoDataFound);
        refresh = v.findViewById(R.id.refresh);
        tools=new Tools(getContext());
        restcall = RestClient.createService(Restcall.class, VariableBag.BASE_URL, VariableBag.API_KEY);


        GetallBloodgroupCall();


        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetallBloodgroupCall();
                refresh.setRefreshing(false);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (allPersonRelationAdapter != null) {
                    allPersonRelationAdapter.Search(charSequence, rcvBloodType);
                }
                boolean isSearchResultsEmpty = allPersonRelationAdapter.isEmpty();
                if (isSearchResultsEmpty) {
                    tvNoDataFound.setVisibility(View.VISIBLE);
                    tvNoData.setVisibility(View.VISIBLE);
                } else {
                    tvNoDataFound.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.GONE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return v;
    }

    private void GetallBloodgroupCall() {
        tools.showLoading();
        restcall.GetallBloodgroups("getallBloodgroups")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<BloodDonateListResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Log.e("API Error", "Error: " + e.getLocalizedMessage());
                                Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(BloodDonateListResponse bloodDonateListResponse) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (bloodDonateListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)) {

                                    List<BloodDonateListResponse.GetBloodGroup> filteredList = filterData(bloodDonateListResponse.getGetBloodGroupList(), "A+");

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                    rcvBloodType.setLayoutManager(layoutManager);
                                    allPersonRelationAdapter = new AllPersonRelationAdapter(getContext(), filteredList);
                                    rcvBloodType.setAdapter(allPersonRelationAdapter);

                                    allPersonRelationAdapter.SetUpInterFace(new AllPersonRelationAdapter.EditClick() {
                                        @Override
                                        public void EditPage(BloodDonateListResponse.GetBloodGroup bloodGroup) {
                                            String requestId = bloodGroup.getRequestId();
                                            EditStatus(requestId);

                                        }

                                        @Override
                                        public void FilterDialog(BloodDonateListResponse.GetBloodGroup bloodGroup) {
                                            FragmentManager fragmentManager = getChildFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            CongressFilterFragment congressFilterFragment = new CongressFilterFragment();
                                            congressFilterFragment.show(fragmentTransaction, "#tag");
                                            congressFilterFragment.setCancelable(false);
                                        }
                                    });

                                    if (filteredList.isEmpty()) {
                                        tvNoDataFound.setVisibility(View.VISIBLE);
                                        tvNoData.setVisibility(View.VISIBLE);
                                    } else {
                                        tvNoDataFound.setVisibility(View.GONE);
                                        tvNoData.setVisibility(View.GONE);
                                    }
                                }
                              //  Toast.makeText(getContext(), bloodDonateListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    private List<BloodDonateListResponse.GetBloodGroup> filterData(List<BloodDonateListResponse.GetBloodGroup> dataList, String bloodGroup) {
                        List<BloodDonateListResponse.GetBloodGroup> filteredList = new ArrayList<>();
                        for (BloodDonateListResponse.GetBloodGroup item : dataList) {
                            if (item.getBloodGroup().equalsIgnoreCase(bloodGroup)) {
                                filteredList.add(item);
                            }
                        }
                        return filteredList;
                    }
                });
        }

    private void EditStatus(String requestId) {
        tools.showLoading();

        restcall.EditStatus("ActivateCategory",requestId,"0")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<EditStatusListResponse>() {
                    @Override
                    public void onCompleted() {
                        GetallBloodgroupCall();
                    }

                    @Override
                    public void onError(Throwable e) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Log.e("API Error", "Error: " + e.getLocalizedMessage());
                                Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(EditStatusListResponse editStatusListResponse) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (editStatusListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)){

                                }
                              //  Toast.makeText(getContext(), editStatusListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }


   /*
    //flag 1 position
    private void GetallBloodgroupCall() {
        tools.showLoading();
        restcall.GetallBloodgroups("getallBloodgroups")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<BloodDonateListResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Log.e("API Error", "Error: " + e.getLocalizedMessage());
                                Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(BloodDonateListResponse bloodDonateListResponse) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (bloodDonateListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)) {
                                    List<BloodDonateListResponse.GetBloodGroup> filteredList = filterData(bloodDonateListResponse.getGetBloodGroupList());

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                    rcvBloodType.setLayoutManager(layoutManager);
                                    allPersonRelationAdapter = new AllPersonRelationAdapter(getContext(), filteredList);
                                    rcvBloodType.setAdapter(allPersonRelationAdapter);
                                }
                                Toast.makeText(getContext(), bloodDonateListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    private List<BloodDonateListResponse.GetBloodGroup> filterData(List<BloodDonateListResponse.GetBloodGroup> dataList) {
                        List<BloodDonateListResponse.GetBloodGroup> filteredList = new ArrayList<>();
                        for (BloodDonateListResponse.GetBloodGroup item : dataList) {
                            boolean isFlagTrue = item.isFlag(); // Replace with your actual method to get the flag

                            if (isFlagTrue) {
                                filteredList.add(item);
                            }
                        }
                        return filteredList;
                    }
                });
    }*/

   /* //privious date (data)
    private void GetallBloodgroupCall() {
        tools.showLoading();
        restcall.GetallBloodgroups("getallBloodgroups")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<BloodDonateListResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Log.e("API Error", "Error: " + e.getLocalizedMessage());
                                Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(BloodDonateListResponse bloodDonateListResponse) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (bloodDonateListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)) {
                                    String currentDate = getCurrentDate(); // Replace with your method to get current date
                                    List<BloodDonateListResponse.GetBloodGroup> pastDataList = filterPastData(bloodDonateListResponse.getGetBloodGroupList(), currentDate);

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                    rcvBloodType.setLayoutManager(layoutManager);
                                    allPersonRelationAdapter = new AllPersonRelationAdapter(getContext(), pastDataList);
                                    rcvBloodType.setAdapter(allPersonRelationAdapter);
                                }
                                Toast.makeText(getContext(), bloodDonateListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    private List<BloodDonateListResponse.GetBloodGroup> filterPastData(List<BloodDonateListResponse.GetBloodGroup> dataList, String currentDate) {
                        List<BloodDonateListResponse.GetBloodGroup> pastDataList = new ArrayList<>();
                        for (BloodDonateListResponse.GetBloodGroup item : dataList) {
                            String itemDate = item.getDate();
                            if (compareDates(itemDate, currentDate) < 0) {
                                pastDataList.add(item);
                            }
                        }
                        return pastDataList;
                    }

                    private String getCurrentDate() {
                        // Implement your logic to get the current date
                        // For example, you can use SimpleDateFormat to format the date
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        return sdf.format(new Date());
                    }

                    private int compareDates(String date1, String date2) {
                        return date1.compareTo(date2);
                    }
                });
    }*/


}