//index.js
const app = getApp()
const myurl = app.globalData.myurl
Page({
  data: {
    username:'',
    password:'',
    authcode:'',
    yanzheng_img: '' //验证码图片 
  },
  usernameInput: function (event) {
    this.setData({ username: event.detail.value })
  },

  passwordInput: function (event) {
    this.setData({ password: event.detail.value })
  },

  authcodeInput: function (event) {
    this.setData({ authcode: event.detail.value })
  },

  yanzhengma_refresh:function(options){
    var self = this
    wx.request({
      url: myurl+'jw/captcha',
      data: '',
      header: {},
      method: 'GET',
      success: function (res) {
        self.setData({
          yanzheng_img: res.data.code,
        });
        wx.setStorageSync('cookie', res.data.cookie)
      }
    })
  },
  onLoad:function(options){
    this.yanzhengma_refresh(options.id);
  },

  loginBtnClick: function (e) {
    
    var username=this.data.username;
    var pass=this.data.password;
    var authcode = this.data.authcode;
    var cookie = wx.getStorageSync('cookie');

    if(username.length== 0) {
      wx.showModal({
      title: '学号不得为空',
      showCancel: false
      })
    }
    else if(pass.length == 0) {
      wx.showModal({
        title: '密码不得为空',
        showCancel: false
      })
    }
    else if (authcode.length==0) {
      wx.showModal({
        title: '验证码不得为空',
        showCancel: false
      })
    }
    else{
       var self= this
       wx.login({
         success:function(res){
           var that = self
           if (res.code) {
             wx.request({
               url:myurl + 'jw/login',
              header: {
                 'content-type': 'application/json'
               },
              method:"post",
              data:{
                "username":username,
                "password":pass,
                "captcha":authcode,
                "cookie":cookie
                },
              success:function(res){
                var successcookie = res.data.cookie;
                wx.request({
                  url: myurl + 'jw/course',
                  header: {
                    'content-type': 'application/json'
                  },
                  method: "post",
                  data: {
                    "cookie": successcookie,
                    "year": "2019",
                    "term": 1
                  },
                  success: function (res) {
                    wx.setStorageSync("timetabledata",res.data);
                  }
              })


                wx.showModal({
                  title: res.data.msg,
                  showCancel:false
                })
                var uName=wx.getStorageSync("uName");
                
                if(res.data.isLoginSuccess==true){
                  setTimeout(function () {
                    if(!uName){
                      wx.navigateTo({
                        url:'../homepage/homepage',
                      })
                    } else if (uName.indexOf('grade') >= 0) 
                      {
                      wx.redirectTo({
                        url: '../grade/gradeset',
                      })
                    }else{
                      wx.redirectTo({
                        url: '../' + uName + '/' + uName,
                      })
                    }                     
                    }, 300)
                  wx.setStorageSync('successcookie', res.data.cookie);
                  wx.setStorageSync('msg', res.data.msg);
                }else{
                  that.yanzhengma_refresh(e.id);
                }
              }
            })
          }
         }
       })
    }
  }
})
