//timetable.js
const app = getApp()
const myurl = app.globalData.myurl
Page({
  data: {
    totaldata: [],
    week_kecheng: [],
    years: ["2019", "2018", "2017", "2016","2015"],
    yearIndex: 0,
    weeks: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20'],
    weekIndex:0,
    terms: ["春", "秋", "夏"],
    termIndex: 0,
    weeklist: ["周日", "周六", "周五", "周四", "周三", "周二", "周一"],
    times: ['1','2','3','4','5','6','7','8','9','10']
  },
  timetable_fresh:function(){
    var that = this;
    //清空week_kecheng数组
    that.setData({
      week_kecheng: []
    })
  var totaldata = that.data.totaldata;
    var week = that.data.weeks[that.data.weekIndex];
    var random = 0;
    for(var i = 0; i<totaldata.length; i++) {

  for (var j = 0; j < totaldata[i].timeAndPlace.length; j++) {
    var temp1 = totaldata[i].timeAndPlace[j];
    for (var k = 0; k < temp1.weeks.length; k++) { if (week == temp1.weeks[k]) break; }
    if (k < temp1.weeks.length) {
      var tmp =
      {
        "courseid": i,
        "xqj": temp1.weekday,
        "skjc": temp1.start,
        "skcd": 2,
        "kcmc": totaldata[i].name,
        "place": temp1.place,
        "bg": 'kcb_class_color' + random
      };
      that.data.week_kecheng.push(tmp);
    }
  }
  random++;

  if (random == 7)
    random = 0;
}
that.setData({
  week_kecheng: that.data.week_kecheng
});
wx.setStorageSync("timetable_already", that.data.week_kecheng)
},

ytchange_timetablefresh:function(){
  var that = this;
  var cookie = wx.getStorageSync('successcookie');
  var term = that.data.termIndex - '0' + 1;
  wx.request({
    url: myurl + 'jw/course',
    header: {
      'content-type': 'application/json'
    },
    method: "post",
    data: {
      "cookie": cookie,
      "year": that.data.years[that.data.yearIndex],
      "term": term
    },

    success: function (res) {

      that.setData({
        totaldata: res.data
      }),
      wx.setStorageSync("timetabledata", res.data);
      that.timetable_fresh();
    },
    fail: function (res) {
      wx.showModal({
        title: "网络请求错误，课程表未更新",
        showCancel: false
      })
      var timetabledata = wx.getStorageSync("timetabledata");
      that.setData({
        dataset: timetabledata,
      })
    }
  })
},
  onLoad: function () {
    var  that =this
    var currentweek=wx.getStorageSync("currentweek");
    that.setData({
      weekIndex:currentweek-1
    })
    that.ytchange_timetablefresh();
    that.timetable_fresh();
  },
  gohomepage:function(){
    wx.redirectTo({
       url: '../homepage/homepage',
    })
  },
  bindYearChange: function (e) {
    this.setData({
      yearIndex: e.detail.value
    });
    this.ytchange_timetablefresh();
  },
  bindWeekChange: function (e) {
    this.setData({
      weekIndex: e.detail.value
    });
    this.timetable_fresh();
  },
  bindTermChange: function (e) {
    this.setData({
      termIndex: e.detail.value
    });
    this.ytchange_timetablefresh();
  },
  detailclass:function(e){
    var courseid = e.target.id;
    wx.setStorageSync("detail_classid", courseid);
    wx.navigateTo({
      url: 'detail',
    })
  }
})