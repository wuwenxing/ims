package com.gwghk.ims.message.dao.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Gts2Symbol {
    private Integer id;

    private Integer uuid;

//    private Date uutime;

    private Integer groupid;

    private String name;

    private String source;

    private Short enable;

    private Short digits;

    private Integer contractsize;

    private Short spread;

    private Short spreadbalance;

    private Integer margininitial;

    private Integer marginmaintenance;

    private Integer marginstopout;

    private Integer marginhedged;

    private Integer specialmargininitial;

    private Integer specialmarginmaintenance;

    private Integer specialmarginstopout;

    private Integer specialmarginhedged;

    private Integer holidaymargininitial;

    private Integer holidaymarginmaintenance;

    private Integer holidaymarginstopout;

    private Integer holidaymarginhedged;

    private Double volumesmin;

    private Double volumesstep;

    private Double volumesmax;

    private String volumeslist;

    private Integer quotetimeout;

    private Integer ordertimeout;

    private Short mode;

    private Double minmanualvolume;

    private Short stoplevel;

    private Integer maxstoplevel;

    private Short pipsratio;

    private Double longswap;

    private Double shortswap;

    private Double longswapadjust;

    private Double shortswapadjust;

    private Short threedaysswap;

    private String basecurrency;

    private String profitcurrency;

    private String margincurrency;

    private Short tradable;

    private Integer futurerefidinitial;

    private Double futureswap;

    private Double futuretolerance;

    private Integer rangeinitial;

    private Integer createuserid;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date moditime;

    private Integer modiuserid;

    private Short swapdaysperyear;

    private Short status;

    private Short type;

    private Integer defaultrangeinitial;

    private Double positionvolumesmax;

    private Short calcswapschedule;

    private String displayname;

    private Double futurerefidhighrisk;

    private Double futurerefidblacklist;

    private Double futureboundaryvalue;

    private Double futureplvalue;

    private Short futureuse;

    private Integer rangehighrisk;

    private Integer rangeblacklist;

    private Integer defaultrangehighrisk;

    private Integer defaultrangeblacklist;

    private Integer companyid;

    private Integer calcswaptype;

    private Integer lastmarginset;

    private Integer positionordersmax;
    
    private String env;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

//    public Date getUutime() {
//        return uutime;
//    }
//
//    public void setUutime(Date uutime) {
//        this.uutime = uutime;
//    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Short getEnable() {
        return enable;
    }

    public void setEnable(Short enable) {
        this.enable = enable;
    }

    public Short getDigits() {
        return digits;
    }

    public void setDigits(Short digits) {
        this.digits = digits;
    }

    public Integer getContractsize() {
        return contractsize;
    }

    public void setContractsize(Integer contractsize) {
        this.contractsize = contractsize;
    }

    public Short getSpread() {
        return spread;
    }

    public void setSpread(Short spread) {
        this.spread = spread;
    }

    public Short getSpreadbalance() {
        return spreadbalance;
    }

    public void setSpreadbalance(Short spreadbalance) {
        this.spreadbalance = spreadbalance;
    }

    public Integer getMargininitial() {
        return margininitial;
    }

    public void setMargininitial(Integer margininitial) {
        this.margininitial = margininitial;
    }

    public Integer getMarginmaintenance() {
        return marginmaintenance;
    }

    public void setMarginmaintenance(Integer marginmaintenance) {
        this.marginmaintenance = marginmaintenance;
    }

    public Integer getMarginstopout() {
        return marginstopout;
    }

    public void setMarginstopout(Integer marginstopout) {
        this.marginstopout = marginstopout;
    }

    public Integer getMarginhedged() {
        return marginhedged;
    }

    public void setMarginhedged(Integer marginhedged) {
        this.marginhedged = marginhedged;
    }

    public Integer getSpecialmargininitial() {
        return specialmargininitial;
    }

    public void setSpecialmargininitial(Integer specialmargininitial) {
        this.specialmargininitial = specialmargininitial;
    }

    public Integer getSpecialmarginmaintenance() {
        return specialmarginmaintenance;
    }

    public void setSpecialmarginmaintenance(Integer specialmarginmaintenance) {
        this.specialmarginmaintenance = specialmarginmaintenance;
    }

    public Integer getSpecialmarginstopout() {
        return specialmarginstopout;
    }

    public void setSpecialmarginstopout(Integer specialmarginstopout) {
        this.specialmarginstopout = specialmarginstopout;
    }

    public Integer getSpecialmarginhedged() {
        return specialmarginhedged;
    }

    public void setSpecialmarginhedged(Integer specialmarginhedged) {
        this.specialmarginhedged = specialmarginhedged;
    }

    public Integer getHolidaymargininitial() {
        return holidaymargininitial;
    }

    public void setHolidaymargininitial(Integer holidaymargininitial) {
        this.holidaymargininitial = holidaymargininitial;
    }

    public Integer getHolidaymarginmaintenance() {
        return holidaymarginmaintenance;
    }

    public void setHolidaymarginmaintenance(Integer holidaymarginmaintenance) {
        this.holidaymarginmaintenance = holidaymarginmaintenance;
    }

    public Integer getHolidaymarginstopout() {
        return holidaymarginstopout;
    }

    public void setHolidaymarginstopout(Integer holidaymarginstopout) {
        this.holidaymarginstopout = holidaymarginstopout;
    }

    public Integer getHolidaymarginhedged() {
        return holidaymarginhedged;
    }

    public void setHolidaymarginhedged(Integer holidaymarginhedged) {
        this.holidaymarginhedged = holidaymarginhedged;
    }

    public Double getVolumesmin() {
        return volumesmin;
    }

    public void setVolumesmin(Double volumesmin) {
        this.volumesmin = volumesmin;
    }

    public Double getVolumesstep() {
        return volumesstep;
    }

    public void setVolumesstep(Double volumesstep) {
        this.volumesstep = volumesstep;
    }

    public Double getVolumesmax() {
        return volumesmax;
    }

    public void setVolumesmax(Double volumesmax) {
        this.volumesmax = volumesmax;
    }

    public String getVolumeslist() {
        return volumeslist;
    }

    public void setVolumeslist(String volumeslist) {
        this.volumeslist = volumeslist == null ? null : volumeslist.trim();
    }

    public Integer getQuotetimeout() {
        return quotetimeout;
    }

    public void setQuotetimeout(Integer quotetimeout) {
        this.quotetimeout = quotetimeout;
    }

    public Integer getOrdertimeout() {
        return ordertimeout;
    }

    public void setOrdertimeout(Integer ordertimeout) {
        this.ordertimeout = ordertimeout;
    }

    public Short getMode() {
        return mode;
    }

    public void setMode(Short mode) {
        this.mode = mode;
    }

    public Double getMinmanualvolume() {
        return minmanualvolume;
    }

    public void setMinmanualvolume(Double minmanualvolume) {
        this.minmanualvolume = minmanualvolume;
    }

    public Short getStoplevel() {
        return stoplevel;
    }

    public void setStoplevel(Short stoplevel) {
        this.stoplevel = stoplevel;
    }

    public Integer getMaxstoplevel() {
        return maxstoplevel;
    }

    public void setMaxstoplevel(Integer maxstoplevel) {
        this.maxstoplevel = maxstoplevel;
    }

    public Short getPipsratio() {
        return pipsratio;
    }

    public void setPipsratio(Short pipsratio) {
        this.pipsratio = pipsratio;
    }

    public Double getLongswap() {
        return longswap;
    }

    public void setLongswap(Double longswap) {
        this.longswap = longswap;
    }

    public Double getShortswap() {
        return shortswap;
    }

    public void setShortswap(Double shortswap) {
        this.shortswap = shortswap;
    }

    public Double getLongswapadjust() {
        return longswapadjust;
    }

    public void setLongswapadjust(Double longswapadjust) {
        this.longswapadjust = longswapadjust;
    }

    public Double getShortswapadjust() {
        return shortswapadjust;
    }

    public void setShortswapadjust(Double shortswapadjust) {
        this.shortswapadjust = shortswapadjust;
    }

    public Short getThreedaysswap() {
        return threedaysswap;
    }

    public void setThreedaysswap(Short threedaysswap) {
        this.threedaysswap = threedaysswap;
    }

    public String getBasecurrency() {
        return basecurrency;
    }

    public void setBasecurrency(String basecurrency) {
        this.basecurrency = basecurrency == null ? null : basecurrency.trim();
    }

    public String getProfitcurrency() {
        return profitcurrency;
    }

    public void setProfitcurrency(String profitcurrency) {
        this.profitcurrency = profitcurrency == null ? null : profitcurrency.trim();
    }

    public String getMargincurrency() {
        return margincurrency;
    }

    public void setMargincurrency(String margincurrency) {
        this.margincurrency = margincurrency == null ? null : margincurrency.trim();
    }

    public Short getTradable() {
        return tradable;
    }

    public void setTradable(Short tradable) {
        this.tradable = tradable;
    }

    public Integer getFuturerefidinitial() {
        return futurerefidinitial;
    }

    public void setFuturerefidinitial(Integer futurerefidinitial) {
        this.futurerefidinitial = futurerefidinitial;
    }

    public Double getFutureswap() {
        return futureswap;
    }

    public void setFutureswap(Double futureswap) {
        this.futureswap = futureswap;
    }

    public Double getFuturetolerance() {
        return futuretolerance;
    }

    public void setFuturetolerance(Double futuretolerance) {
        this.futuretolerance = futuretolerance;
    }

    public Integer getRangeinitial() {
        return rangeinitial;
    }

    public void setRangeinitial(Integer rangeinitial) {
        this.rangeinitial = rangeinitial;
    }

    public Integer getCreateuserid() {
        return createuserid;
    }

    public void setCreateuserid(Integer createuserid) {
        this.createuserid = createuserid;
    }

    public Date getModitime() {
        return moditime;
    }

    public void setModitime(Date moditime) {
        this.moditime = moditime;
    }

    public Integer getModiuserid() {
        return modiuserid;
    }

    public void setModiuserid(Integer modiuserid) {
        this.modiuserid = modiuserid;
    }

    public Short getSwapdaysperyear() {
        return swapdaysperyear;
    }

    public void setSwapdaysperyear(Short swapdaysperyear) {
        this.swapdaysperyear = swapdaysperyear;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Integer getDefaultrangeinitial() {
        return defaultrangeinitial;
    }

    public void setDefaultrangeinitial(Integer defaultrangeinitial) {
        this.defaultrangeinitial = defaultrangeinitial;
    }

    public Double getPositionvolumesmax() {
        return positionvolumesmax;
    }

    public void setPositionvolumesmax(Double positionvolumesmax) {
        this.positionvolumesmax = positionvolumesmax;
    }

    public Short getCalcswapschedule() {
        return calcswapschedule;
    }

    public void setCalcswapschedule(Short calcswapschedule) {
        this.calcswapschedule = calcswapschedule;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname == null ? null : displayname.trim();
    }

    public Double getFuturerefidhighrisk() {
        return futurerefidhighrisk;
    }

    public void setFuturerefidhighrisk(Double futurerefidhighrisk) {
        this.futurerefidhighrisk = futurerefidhighrisk;
    }

    public Double getFuturerefidblacklist() {
        return futurerefidblacklist;
    }

    public void setFuturerefidblacklist(Double futurerefidblacklist) {
        this.futurerefidblacklist = futurerefidblacklist;
    }

    public Double getFutureboundaryvalue() {
        return futureboundaryvalue;
    }

    public void setFutureboundaryvalue(Double futureboundaryvalue) {
        this.futureboundaryvalue = futureboundaryvalue;
    }

    public Double getFutureplvalue() {
        return futureplvalue;
    }

    public void setFutureplvalue(Double futureplvalue) {
        this.futureplvalue = futureplvalue;
    }

    public Short getFutureuse() {
        return futureuse;
    }

    public void setFutureuse(Short futureuse) {
        this.futureuse = futureuse;
    }

    public Integer getRangehighrisk() {
        return rangehighrisk;
    }

    public void setRangehighrisk(Integer rangehighrisk) {
        this.rangehighrisk = rangehighrisk;
    }

    public Integer getRangeblacklist() {
        return rangeblacklist;
    }

    public void setRangeblacklist(Integer rangeblacklist) {
        this.rangeblacklist = rangeblacklist;
    }

    public Integer getDefaultrangehighrisk() {
        return defaultrangehighrisk;
    }

    public void setDefaultrangehighrisk(Integer defaultrangehighrisk) {
        this.defaultrangehighrisk = defaultrangehighrisk;
    }

    public Integer getDefaultrangeblacklist() {
        return defaultrangeblacklist;
    }

    public void setDefaultrangeblacklist(Integer defaultrangeblacklist) {
        this.defaultrangeblacklist = defaultrangeblacklist;
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public Integer getCalcswaptype() {
        return calcswaptype;
    }

    public void setCalcswaptype(Integer calcswaptype) {
        this.calcswaptype = calcswaptype;
    }

    public Integer getLastmarginset() {
        return lastmarginset;
    }

    public void setLastmarginset(Integer lastmarginset) {
        this.lastmarginset = lastmarginset;
    }

    public Integer getPositionordersmax() {
        return positionordersmax;
    }

    public void setPositionordersmax(Integer positionordersmax) {
        this.positionordersmax = positionordersmax;
    }

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}
    
    
}