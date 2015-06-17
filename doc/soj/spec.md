Soj
===

### 全称sojourner

### 参数

Soj里参数和日志里Key的对应关系以及含义如下

```
每一行日志开头有一个tracker.chl.biz.XXXXXX.ab:{}的XXXX部分是从参数的p过来的

参数名   aa_log里的key   含义
expid		rid				唯一id
pn      pn				页面
biz		biz			    具体业务［pv、npv］
guid    guid           guid
        uguid          soj.kjt.com域名下的cookie内容
url       url            访问地址
ref       referer        访问referer
chl     chl				渠道[site、tw、pad、app]
        st             服务器时间戳(13位，到毫秒)
        cip            客户端IP地址
ssid    ssid           会话id
lt      lt				loadtime
uid     uid            登录用户id
ctid    cid            城市id
luid    lui
t       ct             客户端时间戳(13位，到毫秒)
cp      cp         		定制参数，内容一般为JSON字符串
        rfpn           referer页面的pn
m       method         http method
        agent          访问者的user agent
ab      ab             AB测试
```

    aa_log里的key变化

    ssid->sessid
    ct->cstamp
    st->stamp
    cp->cstparam
    site->site[新增]
```
最新定义

每一行日志开头有一个tracker.XXXXXX.biz.ab:{}的XXXX部分是从参数的pn读取过来的
参数名   aa_log里的key   含义
expid		rid				唯一id
pn      pn				页面
biz		biz			    具体业务［pv、npv］
guid    guid           guid
        uguid          soj.kjt.com域名下的cookie内容
url     url            访问地址
ref     referer        访问referer
site    site           业务［kjt］
chl     chl			   渠道[site、tw、pad、app]
        stamp          服务器时间戳(13位，到毫秒)
        cip            客户端IP地址
ssid    sessid         会话id
lt      lt				loadtime
uid     uid            登录用户id
ctid    cid            城市id
luid    lui
t       cstamp         客户端时间戳(13位，到毫秒)
cp      cstparam       定制参数，内容一般为JSON字符串
rfpn    rfpn           referer页面的pn
m       method         http method
        agent          访问者的user agent
ab      ab             AB测试
```
