// miniprogram/pages/homepage/homepage.js
const app = getApp()
const myurl = app.globalData.myurl
var util = require("../../assets/util.js")
Page({

  /**
   * 页面的初始数据
   */
  data: {
    a1src:'../homepage/kechengbiao.png',
    a2src: '../homepage/chengji.png',
    a3src: '../homepage/jiaoshi.png',
    a4src: '../homepage/tushuguan.png',
    a5src: '../homepage/dasao.png',
    a6src: '../homepage/rili.png',
    a7src: '../homepage/dianhua.png',
    a8src: '../homepage/ditudaohang.png',
    todayclass: [],
    xqjformat:'',
    currentweek:''
    // weatherData:''
    // city: "",
    // today: {},
    // future: {},
  },
  
  kc:function(){    
    var successcookie = wx.getStorageSync("successcookie");
    wx.setStorageSync("uName", "timetable");
    if (!successcookie){
      wx.redirectTo({
       url: '../index/index'
      })
    }else{
      wx.navigateTo({
        url: '../timetable/timetable',
      })
    }
  },
  cj: function () {
    var successcookie = wx.getStorageSync("successcookie");
    wx.setStorageSync("uName", "grade");
    if (!successcookie) {
      wx.redirectTo({
        url: '../index/index'

      })
    } else {
      wx.navigateTo({
        url: '../grade/gradeset',
      })
    }
  },
  kong: function () {
    var successcookie = wx.getStorageSync("successcookie");
    wx.setStorageSync("uName", "room");
    if (!successcookie) {
      wx.redirectTo({
        url: '../index/index'
      })
    } else {
      wx.navigateTo({
        url: '../room/room',
      })
    }
  },

  logout:function(){
    var _this=this;
    wx.removeStorage({
      key: 'successcookie',
      success: function (res) {
        console.log(res.data)
      },
    })
    wx.removeStorage({
      key: 'uName',
      success: function (res) {
        console.log(res.data)
      },
    })
    wx.removeStorage({
      key: 'msg',
      success: function (res) {
        console.log(res.data)
      },
    })
    wx.removeStorage({
      key: 'cookie',
      success: function (res) {
        console.log(res.data)
      },
    })
    wx.removeStorage({
      key: 'currentweek',
      success: function (res) {
        console.log(res.data)
      },
    }),
    wx.removeStorage({
      key: 'timetable_already',
      success: function (res) {
        console.log(res.data)
      }
    })
    wx.removeStorage({
      key: 'id',
      success: function (res) {
        console.log(res.data)
      },
    })
    wx.removeStorage({
      key: 'myname',
      success: function (res) {
        console.log(res.data)
      },
    })
    wx.removeStorage({
      key: 'myarr',
      success: function (res) {
        console.log(res.data)
      },
    })
    this.setData({
      todayclass: []
    })
    var tmp = {
      "name": "",
      "place": "请先登陆账号",
      "shijian": ""
    }
    _this.data.todayclass.push(tmp);
    this.setData({
      status:"请先登陆账号",
      func: "login",
      msg:"",
      todayclass: _this.data.todayclass
    })
  },
  login:function(){
    wx.navigateTo({
      url: '../index/index',
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var _this = this;
    // 清除地图cookie
    wx.removeStorage({
      key: 'markers',
      success: function (res) {
        // console.log(res.data)
      },
    }),
      wx.removeStorage({
        key: 'uName',
        success: function (res) {
          //console.log(res.data)
        },
      })
    wx.request({
      url: myurl + 'jw/week',
      header: {
        'content-type': 'application/json'
      },
      method: "get",
      success: function (res) {
        var currentweek = res.data.number;
        _this.setData({
          currentweek: "第"+currentweek+"周"
        })
        wx.setStorageSync("currentweek", currentweek);
      }
    }
    )
    // wx.removeStorage({
    //   key: 'markers1',
    //   success: function (res) {
    //     console.log(res.data)
    //   },
    // })
    var msg = wx.getStorageSync("msg");
    var successcookie = wx.getStorageSync("successcookie");
    // console.log("获取到的信息"+msg+successcookie);
    if(!successcookie){
      // console.log("无msg succ");
      var tmp = {
        "name": "",
        "place": "请先登陆账号",
        "shijian":""
      }
      _this.data.todayclass.push(tmp);
      _this.setData({
        status:"请先登陆账号",
        func:"login",
        todayclass: _this.data.todayclass
      });
    }else if(successcookie){
      // console.log("有succ");
      wx.request({
        url: 'https://api.zhpjy.cc/jw/cookie',
        header: {
          'content-type': 'application/json'
        },
        method: "post",
        data: {
          "cookie": successcookie
        },
        success: function (res) {
          var valid=res.data.valid
          // var valid=false;
          // console.log("valid是否有效"+valid)
          if(valid==false){
            wx.removeStorage({
              key: 'successcookie',
              success: function (res) {
                console.log(res.data)
              },
            })
            // wx.removeStorage({
            //   key: 'msg',
            //   success: function (res) {
            //     //console.log(res.data)
            //   },
            // })
            _this.setData({
              status:"登陆",
              func:"login"
            })
          }
          else if(valid==true){
            _this.setData({
              todayclass:[]
            })//清空今日课表
            
            var totaldata = wx.getStorageSync("timetabledata");
            var tempp= [];
            var week = wx.getStorageSync("currentweek");
            for (var i = 0; i < totaldata.length; i++) {
              for (var j = 0; j < totaldata[i].timeAndPlace.length; j++) {
                var temp1 = totaldata[i].timeAndPlace[j];
                for (var k = 0; k < temp1.weeks.length; k++) { if (week == temp1.weeks[k]) break; }
                if (k < temp1.weeks.length) {
                  var tmp =
                  {
                    "courseid": i,
                    "xqj": temp1.weekday,
                    "skjc": temp1.start,
                    "kcmc": totaldata[i].name,
                    "place": temp1.place
                  };
                  tempp.push(tmp);
                }
              }
            }
            wx.setStorageSync("timetable_already", tempp);
            var weekclass = tempp;
            let time = util.formatDate(new Date());
            let date = util.getDates(time);
            var xqj = date.xqj;
            var temp = [];
            for (var i = 0; i < weekclass.length; i++) {
              if (weekclass[i].xqj == xqj) {
                var tmp = {
                  "name": weekclass[i].kcmc,
                  "place": weekclass[i].place,
                  "shijian": weekclass[i].skjc + "-" + (weekclass[i].skjc + 1)
                }
                temp.push(tmp);
              }
            }
            for (var i = 0; i < temp.length; i++) {
              for (var j = 0; j < temp.length - 1 - i; j++) {
                if (temp[j + 1].shijian < temp[j].shijian) {
                  var tmp = temp[j].shijian;
                  temp[j].shijian = temp[j + 1].shijian;
                  temp[j + 1].shijian = tmp
                }
              }
            }
            if (temp.length == 0) {
              var tmp = {
                "name": "",
                "place": "今日没有课程",
                "shijian": ""
              }
              temp.push(tmp);
            }
            _this.setData({
              status: "注销",
              msg: msg,
              func: "logout",
              todayclass: temp,
              xqjformat: date.xqjformat
            })
          }
        },
      
    })
    }
  },
    
    

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    wx.removeStorage({
      key: 'uName',
      success: function (res) {
        // console.log(res.data)
      },
    })
  }
})