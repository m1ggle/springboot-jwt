# 什么是JWT


    JSON Web Token(JWT) is an open standard that defines a compact and self-contained way for securely transmitting information between parties as a JSON object This information can be verified and trusted because it is digitally signed JWTs can be signed using a secret (with the HMAC algorithm) or a public/private key pair using RSA or ECDSA.

```markdown
# 翻译
- 官网地址：https://jwt.io
- 翻译：jsonwebtoken(JWT)是一个开放标准，他定义了一种紧凑的、自包含的方式，用于在各方之间以json对象安全地传输信息，次信息可以验证和信任，因为他是数字签名的，jwt可以使用
秘密(使用HMAC算法)或使用RSA或ECDSA的公钥/私钥对进行签名。

# 通俗解释
- JWT简称JSON Web Token ，也就是通过JSON形式作为Web应用中的令牌，用于在各方之间安全的将信息作为JSON对象传输，在数据传输过程红还可以完成数据加密，签名等相关处理。
```

# JWT 能做什么

```markdown
# 1.授权
- 这是使用JWT的最常见方案，一旦用户登录，每个后续请求将包含JWT，从而允许用户访问该令牌允许的路由，服务和资源，单点登录是当今广泛使用JWT的一项功能，因为它的开销很小并且可以在不同
的域中轻松使用。
# 2.信息交换
- JSON Web Token 是在各方之间安全地传输信息的好方法，因为可以对JWT进行签名，所以您可以确保发件人是他们所说的人，此外，由于签名是使用标头和有效负载计算的，因此您还可以验证内容
是否遭到篡改。
```

# 为什么是JWT

## 基于传统的Session认证

```markdown
# 1. 认证方式
- 我们知道，http协议本身是一种无状态的协议，而这就以为这如果用户向我们的应用提供了用户名和密码来进行用户认证，那么下一次请求时，用户还要再一次进行用户认证才行，因为根据http协议
我们并不知道是哪个用户发出的请求，所有为了让我们的应用能识别是哪个用户发出的请求，我们只能在服务器存储一份用户登录信息，这份登录信息会在响应是传递给浏览器，告诉器保存为cookie，以便
下次请求时发送给我们的应用，这样我们的应用就能识别请求时来自那个用户，这就是传统的基于session认证。
# 2.暴露问题
- 1.每个用户经过我们的应用认证之后，我们的应用都要在服务器端做一次记录，以便用户下次请求的鉴权，通过而言session都是保存在内存中，而随着认证用户的增多，服务器的开销会明显增大。
- 2.用户认证之后，服务器做认证记录，如果认证的记录被保存在内存中的话，这意味着用户下次请求还必须要请求在这台服务器上，这样才能拿到授权的资源，这样在分布式的应用上，相应的限制了
负载均衡的能力，这也以为这限制了应用的扩展能力。
- 3.因为是基于cookie来进行用户识别的，cookie如果呗截获，应用就会很容易受到跨站请求伪造的攻击。

- 4.在前后端分离系统中更加痛苦；
也就是说前后端分离在应用解耦后增加了部署的复杂性，通常用户一次请求就要转发多次，如果session每次携带sessionid到服务器，服务器还要查询用户信息，同时如果用户很多，这些信息通常
存储在服务器内存中，给服务器增加负担，还有就是CSRF(跨站伪造请求攻击)攻击，session是基于cookie进行用户识别的，如果cookie别截获，用户很容易受到跨站伪造工具，还有就是sessionid
就是一个特征值，表达的信息不够丰富，不容易扩展，而且你后端应用如果部署的是多节点，那么就需要实现session共享机制，不方便集群应用。

```

## 基于JWT认证

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a4885a0f-b253-4be2-8509-664010fe0c1d/Untitled.png)

```markdown
# 1.认证流程
- 首先，前端通过Web表单将自己的用户名密码发送到后端的接口，这一过程一般是一个HTTP POST请求，建议的方式是通过SSL加密的传输（https协议），从而避免敏感信息被嗅探。
- 后端核对用户名和密码成功后，将用户的id等信息作为JWT Playload(负载)，将其余头部分别进行Base64编码拼接后签名，形成一个JWT，形成的JWT就是一个形同lll.xxx.zzz的字符串
- 后端将JWT字符串作为登录成功的返回结果返回给前端，前端可以将返回的结果保存在localStotrge或sessionStorage上，退出登录时前端删除保存的JWT信息即可。
- 前端每次请求时将JWT放入HTTP Header中的Authorization位，(解决XSS和XSEF问题)
- 后端检查是否存在，如存在验证JWT的有效性，例如：检查签名是否正确，检查Token是否过期，检查Token的接收方时候是自己。
- 验证通过后，后端使用JWT中包含的用户信息进行其他逻辑操作，返回响应结果。
# 2.JWT优势
- 简洁：可以通过URL POST参数或者在HTTP Header发送，因为数据量小，传输速度也很快。
- 自包含：负载中包含了所用用户需要的信息，避免了多次查询数据库。
- 因为Token是以JSON加密的形式保存在客户端的，所以JWT是跨语言的，原则上任何Web形式都支持。
- 不需要在服务器保存会话信息，特别实用与分布式微服务。
```

# JWT的结构是什么

```markdown
# 1.令牌组成
- 1.标头(Header)
- 2.有效载荷(Payload)
- 3.签名(Signtture)
- 因此，JWT通常如下：xxxxxxx.yyyyyyyy.zzzzzz      Header.Payload.Signature
```

```markdown
# 2.Header
- 标头通常由两部分组成，令牌的类型，(即JWT) 和所使用的签名算法，例如HMAC/SHA256或RSA，它会实用Base64 编码组成JWT结构的第一部分。
- 注意：Base64是一种编码，也就是说，它是可以被翻译会原来的样子的，它并不是一种加密过程。
```

```json
{
	"alg" : "HS256",
	"typ" : "JWT"
}
```

```markdown
# 3.Payload
- 令牌的第二部分是有效负载，其中包含声明，声明是有关实体，和其他数据的声明，同样的，他会实用Base64编码组成 JWT结构的第二部分。
```

```json
{
	"sub":"1234567890",
	"name":"John Don",
	"admin":true
}
```

```markdown
# 4.Signature
- 前边两部分都是使用 Base64 进行编码，即前端可以解开知道里边的信息，Signature 需要使用编码后的Header 和 Payload 以及我们提供的一个秘钥，然后使用 Header 中指定的签名算法
（HS265）进行签名，签名额作用是保证 JWT 没有被篡改过。
- 如：
	HMACSHA256(base64UrlEncode(header)) + "." + base64UrlEncode(payload,secret);
# 签名目的
- 最后一步签名的过程，实际上是对头部以及负载内容进行签名，防止内容被篡改，如果有人对头部以及负载的内容解码之后进行修改，在进行编码，最后加上之前头部的签名组合形成形成新的JWT，那么
服务器会判断出新头部和负载形成的签名和JWT附带上的签名是不一样的，日过要对新的头部和负载进行签名，在不知道服务器加密是用的秘钥的话，的出来的签名是不一样的。

# 信息安全问题
- 在这里大家一定会问，Base64是一种编码，是可逆的，那么我的信息不就被暴露了吗？

- 是的，所有在JWT中，不应该在负载里面加入任何敏感数据，在上面的例子中，我们传输的是用户的 User ID，这个值实际上不是什么敏感内容，一般情况下被知道也是安全的，但是像密码这样的内容
就不能被放在JWT中，如果将用户的密码存在JWT中，那么怀有恶意的第三方通过Base64解码就能够很快的知道你的密码，因此JWT适用于想web应用中传递一般费敏感信息，JWT还经常用户设计用户仁恒
和授权系统，甚至实现Web应用的单点登录。
```


```markdown
# 5.放在一起
- 输出时三个有点分割的Base64-URL字符串，可以在HTML和HTTP环境中轻松传递这些字符串，与基于XML的标准相比，他更紧凑。
- 简洁：
	可以通过URL、POST参数或者在HTTP Header发送，因为数据量小，传输速度快
- 自包含
	负载中包含了所有用户所需要的信息，避免了多次查询数据库。
```
