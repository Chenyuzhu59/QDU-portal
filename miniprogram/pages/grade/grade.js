// pages/grade/grade.js
const app = getApp()
const myurl = app.globalData.myurl
Page({
  data: {
    dataset: ''
  },

  refresh_grade:function(){
    var that = this;
    var cookie = wx.getStorageSync('successcookie');
    wx.request({
      url: myurl+ 'jw/score',
      header: {
        'content-type': 'application/json'
      },
      method: "post",
      data: {
        "cookie": cookie
      },
      success: function (res) {
        var totaldata = res.data;
        var result= [];
        var year = wx.getStorageSync("chooseyear");
        var term = wx.getStorageSync("chooseterm");
        for(var i=0;i<totaldata.length;i++){
          if(totaldata[i].year == year && totaldata[i].term == term){
            var tmp={
              assess: totaldata[i].assess,
              course: totaldata[i].course,
              credit: totaldata[i].credit,
              score: totaldata[i].score,
              term: totaldata[i].term,
              year: totaldata[i].year,
            }
            result.push(tmp);
          }
        }

        that.setData({
          dataset: result,
        })
        wx.setStorageSync("gradedata", result);
      },
      fail: function (res) {
        wx.showModal({
          title: "网络请求错误，成绩查询未更新",
          showCancel: false
        })
        var totaldata = wx.getStorageSync("gradedata");
        that.setData({
          dataset: totaldata,
        })
      }
    })
  },
  onLoad: function (options) {
    this.refresh_grade();
  },
  gohomepage: function () {
    wx.redirectTo({
      url: '../homepage/homepage',
    })
  },
  bindrefresh: function () {
    wx.navigateTo({
      url: 'gradeset',
    })
  },
})

