var QQMapWX = require('../../lib/qqmap-wx-jssdk.js');
var qqmapsdk;

Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // wx.removeStorage({
    //   key: 'markers1',
    //   success: function (res) {
    //     console.log(res.data)
    //   },
    // })
    qqmapsdk = new QQMapWX({
      key: '22VBZ-REEK5-WVSI7-QKCOP-QPM6E-W7BPO'
    });

    var _this = this;
    qqmapsdk.search({
      keyword: '青岛大学教学楼',
      page_size: '20',
      location: '36.0721730000,120.4202090000',
      page_index: 1,
      success: function (res) {
        var mks1 = [];
        for (var i = 0; i < res.data.length; i++) {
          var name = res.data[i].title;
          if(name.indexOf("师范学院")<0){
            mks1.push({
              title: res.data[i].title,
              id: res.data[i].id,
              address: res.data[i].address,
              latitude: res.data[i].location.lat,
              longitude: res.data[i].location.lng,
              iconPath: "",
              width: 20,
              height: 20
            })
          }   
        }
        wx.setStorageSync("markers1", mks1)
      }
    });
    qqmapsdk.search({
      keyword: '青岛大学教学楼',
      page_size: '20',
      location: '36.0721730000,120.4202090000',
      page_index: 2,
      success: function (res) {
        var mks2 = [];
        mks2 = wx.getStorageSync("markers1");
        for (var i = 0; i < res.data.length; i++) {
          var name = res.data[i].title;
          if (name.indexOf("师范学院") < 0) {
            mks2.push({
              title: res.data[i].title,
              id: res.data[i].id,
              address: res.data[i].address,
              latitude: res.data[i].location.lat,
              longitude: res.data[i].location.lng,
              iconPath: "",
              width: 20,
              height: 20
            })
          }
        }
        mks2.push({
          title: "青岛大学-东12教",
          id: "",
          address: "青岛市崂山区青岛大学东院内",
          latitude: 36.0705810000, 
          longitude: 120.4272050000,
          iconPath: "",
          width: 20,
          height: 20
        })
        wx.setStorageSync("markers", mks2),
        _this.setData({
          markers: mks2
        })
      },
    })
  },
 
  east:function(){
    var mksa = wx.getStorageSync("markers");
    var mkse=[];
    for(var i=0;i<mksa.length;i++){
      var add=mksa[i].address;
      if(add.indexOf("东院")>=0&&add.indexOf("东校区")<0||add.indexOf("青大路交叉口")>=0){
        mkse.push({
          title: mksa[i].title,
          id: mksa[i].id,
          address: mksa[i].address,
          latitude: mksa[i].latitude,
          longitude: mksa[i].longitude,
          iconPath: "../map/map1.png",
          width: 30,
          height: 30
        })
      }
    }
    this.setData({
      markers: mkse
    })
  },
  west: function () {
    var mksa = wx.getStorageSync("markers");
    var mksw = [];
    for (var i = 0; i < mksa.length; i++) {
      var add = mksa[i].address;
      var name=mksa[i].title;
      if (add.indexOf("西院") >= 0 || add.indexOf("博") >= 0 || name.indexOf("博") >= 0 || name.indexOf('浩园') >= 0) {
        mksw.push({
          title: mksa[i].title,
          id: mksa[i].id,
          address: mksa[i].address,
          latitude: mksa[i].latitude,
          longitude: mksa[i].longitude,
          iconPath: "../map/map2.png",
          width: 30,
          height: 30
        })
      }
    }
    this.setData({
      markers: mksw
    })
  },
  north: function () {
    var mksa = wx.getStorageSync("markers");
    var mksnm = [];
    for (var i = 0; i < mksa.length; i++) {
      var add = mksa[i].address;
      var name = mksa[i].title;
      if (add.indexOf("北院") >= 0||name.indexOf("北院")>=0){
        mksnm.push({
          title: mksa[i].title,
          id: mksa[i].id,
          address: mksa[i].address,
          latitude: mksa[i].latitude,
          longitude: mksa[i].longitude,
          iconPath: "../map/map3.png",
          width: 30,
          height: 30
        })
      }
    }
    this.setData({
      markers: mksnm
    })
  },
  hy: function () {
    var mksa = wx.getStorageSync("markers");
    var mksh = [];
    for (var i = 0; i < mksa.length; i++) {
      var add = mksa[i].address;
      var name = mksa[i].title;
      if (name.indexOf("浩")>=0||add.indexOf("浩")>0) {
        mksh.push({
          title: mksa[i].title,
          id: mksa[i].id,
          address: mksa[i].address,
          latitude: mksa[i].latitude,
          longitude: mksa[i].longitude,
          iconPath: "../map/map4.png",
          width: 30,
          height: 30
        })
      }
    }
    this.setData({
      markers: mksh
    })
  },
  gothere:function(e){
    var title = e.currentTarget.dataset.title;
    var lat = e.currentTarget.dataset.lat;
    var lon = e.currentTarget.dataset.lon;
    var add = e.currentTarget.dataset.address;
    wx.getLocation({
      type: 'gcj02',
      success: function (res) {
        wx.openLocation({
          latitude: lat,
          longitude: lon,
          name: title,
          address: add
        })
      }
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})