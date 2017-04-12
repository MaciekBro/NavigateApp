package com.maciekbro.navigateapp.views;

import android.content.Context;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;

import com.google.android.gms.maps.model.LatLngBounds;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.maciekbro.navigateapp.R;
import com.maciekbro.navigateapp.model.dto.SearchParamsDto;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by RENT on 2017-04-12.
 */

public class SearchQueryView extends LinearLayout {

    private EditText editText;
    private RadioButton nerbyRadio, textRadio, radialRadio;
    private AppCompatSeekBar radiusSeekBar;
    private SearchParamsDto searchParamsDto;
    private Map<String, String> querys;
    private String obligateKey = "keyword"; //bedzie zmienny
    private Button searchButton;

    private PublishSubject<SearchParamsDto> subject;

    public SearchQueryView(Context context) {
        super(context);
    }

    public SearchQueryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchQueryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.search_layout, this);
        this.setOrientation(VERTICAL);
        subject = PublishSubject.create(); //

        searchParamsDto = new SearchParamsDto();
        querys = new HashMap<>();
        querys.put("location", "52.245231,21.093469");
        editText = (EditText) this.findViewById(R.id.search_query);
        radialRadio = (RadioButton) findViewById(R.id.radial_search);
        textRadio = (RadioButton) findViewById(R.id.text_search);
        nerbyRadio = (RadioButton) findViewById(R.id.nearby_search);
        radiusSeekBar = (AppCompatSeekBar) findViewById(R.id.seek_bar);
        searchButton = (Button) findViewById(R.id.search_button);

        seUpListeners();

        searchButton.setOnClickListener(new OnClickListener() {     //tworzymy nasz strumień Rx'owy
            @Override
            public void onClick(View v) {
                searchParamsDto.setQuerys(querys);
                subject.onNext(searchParamsDto);

            }
        });
    }

    public Observable<SearchParamsDto> subscribeToStream() {
        return subject;
    }

    @Override
    protected void onDetachedFromWindow() {     //kiedy widok jest niszczony
        super.onDetachedFromWindow();
        subject.onComplete();   //zamkniemy strumien, brak wyciekow pamięci
    }

    private void seUpListeners() {
        RxTextView.textChanges(editText).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(it -> {
                    querys.put(obligateKey, it.toString());
                }, error -> {
                    error.printStackTrace();
                });

        textRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                searchParamsDto.setSearchType("textsearch");
                removeAndAddObligateKeyAfterChange("query");

//                querys.remove(obligateKey);     //usuwanie wczesniejszego keyword po zmianie buttona..
//                obligateKey="query";
//                querys.put(obligateKey,editText.getText().toString());
            }
        });
        nerbyRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                searchParamsDto.setSearchType("nerbysearch");
            }
        });
        radialRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                searchParamsDto.setSearchType("radarsearch");
                removeAndAddObligateKeyAfterChange("keyword");
            }
        });
        radiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                querys.put("radius", String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void removeAndAddObligateKeyAfterChange(String key) {
        querys.remove(obligateKey);
        obligateKey = key;
        querys.put(obligateKey, editText.getText().toString());
    }

    public SearchParamsDto getSearchParamsDto() {
        return null;
    }
}
