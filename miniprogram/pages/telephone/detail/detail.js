
Page({

  /**
   * 页面的初始数据
   */
  data: {
    myname:'',
    myarr:'',
    text: '\n',
    // jw1: [{
    //   "name": "教务处处长",
    //   "tele": "0532-85951877"
    // },
    // {
    //   "name": "教务处副处长",
    //   "tele": "0532-85953715"
    //   },
    //   {
    //     "name": "教务处副处长",
    //     "tele": "0532-85950028"
    //   }]
  },
  call: function (e) {
    var telephone = e.currentTarget.dataset.tele;
    wx.makePhoneCall({
      phoneNumber: telephone,
      success: function () {
        console.log("success")
      },
      fail: function () {
        console.log("fail")
      }
    })
  },
  onLoad: function (options) {
    var myname = wx.getStorageSync("myname");
    var myarr = wx.getStorageSync("myarr");
    // console.log(myname);
    // console.log(myarr);
    this.setData({
      myname:myname,
      myarr:myarr
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