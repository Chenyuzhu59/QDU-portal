// miniprogram/pages/grade/gradeset.js
Page({
  data: {
    years: ["2019","2018", "2017", "2016", "2015"],
    yearIndex: 0,
    terms: ["春", "秋", "夏"],
    termIndex: 0
  },
  bindsave:function(){
    var that=this;
    wx.redirectTo({
      url: 'grade',
    })
    wx.setStorageSync("chooseyear", that.data.years[that.data.yearIndex]);
    wx.setStorageSync("chooseterm", that.data.terms[that.data.termIndex]);
  },

  bindYearChange: function (e) {
    this.setData({
      yearIndex: e.detail.value
    });
  },
  bindTermChange: function (e) {
    this.setData({
      termIndex: e.detail.value
    });
  }
})