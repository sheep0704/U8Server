/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50511
Source Host           : localhost:3306
Source Database       : udb

Target Server Type    : MYSQL
Target Server Version : 50511
File Encoding         : 65001

Date: 2015-08-10 14:03:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `uchannel`
-- ----------------------------
DROP TABLE IF EXISTS `uchannel`;
CREATE TABLE `uchannel` (
  `channelID` int(11) NOT NULL,
  `appID` int(11) NOT NULL,
  `cpAppID` varchar(255) DEFAULT NULL,
  `cpAppKey` varchar(1024) DEFAULT NULL,
  `cpAppSecret` varchar(1024) DEFAULT NULL,
  `cpID` varchar(255) DEFAULT NULL,
  `cpPayID` varchar(255) DEFAULT NULL,
  `cpPayKey` varchar(1024) DEFAULT NULL,
  `cpPayPriKey` varchar(1024) DEFAULT NULL,
  `masterID` int(11) NOT NULL,
  `cpConfig` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`channelID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uchannel
-- ----------------------------
INSERT INTO `uchannel` VALUES ('10', '1', '544156', '7f768353ed0854f58a8d7f3972949aac', null, '593', null, '', null, '1', null);
INSERT INTO `uchannel` VALUES ('11', '1', '2576', 'Jy3ERn6c', 'MIICXQIBAAKBgQCca8WkBCLx4GTbSCULNC2Hwgzj9IEu3R9rBx30aPT6i3h7OuCc21+jOzmHRKde4xKMs+wr9uUEUatU38PDRS4CtKyZ5LJpJQfAlKhOhYYUlTzfsUBBtxneSvgPAfz9mOXKYqfltRPLE4t5H/bap459Mg+nDl1BjsCe5bDHTNsJRQIDAQABAoGAQ3sXA6ApNzH4MMWZ/ZBgCA5GI12LEZ2hfTnPyW4yKZhbvMX0upbFVpUpR1rt3biWew7v1zOnL0rQYEg8mxRQOXgSKKGqZhjCUMvM7auVJn/dfTPxBc+lXGqjgNfkTpDnz6zKkAMGxMeBmIaSJ0/KZc/Ypq/B2q5oXudcPRnOFu0CQQD3ONXm+P58b1viYk5IjL6Kq0Z2n8kPCgP30y52xqs3UG4NLz72qorCAbo+GhmiRCJSkfediwYynozhhYBffZSjAkEAofmV+jOn0h/4teExLGyAIHZ6v1Zz0LclBBbbNQLjPk/9Asq0p8SDmb6yTdBZrBixuOe8mjRsvb2t8pMg/Pbg9wJBAM8djY6gwcuMG+WQSFddmnBgR3swKGhyEpr4ELAvhtejiV+AWnaar/8TJW3LvfgEruCQK2uQQlP8Irj5eHAHrJMCQHbxjz8Cu9REAiGd0rxqmNQ97PmhBL9aHmaYWjTdf/58QrHQJUWlu3YlYU5fF9qW77t3ATrEEK5tIdWmUW0x5hUCQQCck1MtcBJ4toDbGwVKuqVE6DD6cIQwRa3C7+KdlqeNBLqrc9jc+m4Abid8i4Op8nIYAyAQg3DRYpRV2nqW7aMW', '27', null, 'mDf3mu88jjGz', null, '2', null);
INSERT INTO `uchannel` VALUES ('12', '1', '5434671', 'E0OLS0UItr7XqI1BHQi1X6pm', 'mMNF67wSCTxB0tMtIsBzxOQ6VittNg3G', '', null, '', null, '4', null);
INSERT INTO `uchannel` VALUES ('16', '1', '202307546', '765b6459a63959a58fa7ef6a9396ad97', '54c85c3235cfbbbb8973bfccece34767', '', null, '', null, '3', null);
INSERT INTO `uchannel` VALUES ('17', '1', '2882303761517311003', '5991731177003', 'Q6YX7b+/t41Z8KB7Fv7Vsw==', '', null, '', null, '5', null);
INSERT INTO `uchannel` VALUES ('18', '1', '', '102124', 'e091face5ab794ef20edcb8b4600a162', '', null, '', null, '6', null);
INSERT INTO `uchannel` VALUES ('19', '1', '668789', '29iyk1wgOm3o4wscgGcW4gK0w', '98a6B089e632634754740e99b63Edfc1', '', null, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDtJvawWjhQhI+J3EnD3gvuh+t1zB4bOMW9PJUdk27YQDyiGVd42QdHLofdTN1yXKXYZR1Bmy4W1pZhucSoDdS7fGfkKHm3zRMsijNOiPWHg0spMEchI4YTlIC43iFVdzSPE/2sIZfrW/9MspXfuWqFySsTsf6c6qJc6A0bNKJhMwIDAQAB', null, '7', null);
INSERT INTO `uchannel` VALUES ('20', '1', '85597332fabb1b310e5247e57ea2798c', 'd769e63424124aed4c18084ad4057931', '', '20140827163941369124', null, '', null, '8', null);
INSERT INTO `uchannel` VALUES ('21', '1', '', '9a10a0743c351d4cbccd974582aec612', '54f67e269a9c7', '', null, '', null, '9', null);
INSERT INTO `uchannel` VALUES ('22', '1', '', '100024851', '937c0b90cc4e8fb3b067a0c02f9383bb', '', null, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDtJvawWjhQhI+J3EnD3gvuh+t1zB4bOMW9PJUdk27YQDyiGVd42QdHLofdTN1yXKXYZR1Bmy4W1pZhucSoDdS7fGfkKHm3zRMsijNOiPWHg0spMEchI4YTlIC43iFVdzSPE/2sIZfrW/9MspXfuWqFySsTsf6c6qJc6A0bNKJhMwIDAQAB', null, '10', null);
INSERT INTO `uchannel` VALUES ('23', '1', '1410232134070.app.ln', 'MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIOH4o9cE0zxHr/IvD7fdoeM/1oamSBwQjS61OqlmG6jhoZ1gmFMbdlH+HCO/EZ8KOQ9JSux9YhEGK+KVhxGdZ0lN/1YhRH0Ynk0zJooocXLq87S2scPem3GWAESlKsg605PutnoDyKwQvdns67nPZvwRRMeh7nfXMiFBWdXtwEzAgMBAAECgYA9mhjMF82aTZufKv6vW62B0tGNe8OX47u+QnqR4zi/KKtKsiJ8O3V/PCvpW65fvKrSKqkMC+75ARumq12lJILUhBIVj8Ygcn8BYy1BtNeCTwAxCwJtOEyaiFr0sglJbJRLN3OVJNFfO6zgmiqY1/ni6Lx/d2t6fUVU8qZu5TNP0QJBAOe5lCbbha7rHQxcFmEgOnDe3yYf7K8V7ZiM9qWzkEjuxpig9sZPu272R2eGzck+mkls3vqjNcNKl7BF4U2VTNUCQQCRT0pvkTm6yWnrCDxeOTT17XrmMxpXXqfN7tQ0WSk0+/Kw9Lesz6IhJ57YGRqtgvGu2zRblPdVkjsKMtMw9nnnAkA8q6stjVZwGODvJoE5ht2mRcQ5UCyBHwWpZmcBtYT2g4X92k8iVyflAphpc7MXmMt+pAGxr9/YtQQIRBOcY5XNAkBYoGfiDE2No3M6qtdHENVAegvPg7O5Pj5S2CwNkaQUcObhDyFIAYv9dNDpNMaUtZz67S/N+9mvE3V3DvDImExZAkBq6iJqFZx82UR1w+a+l56dlyPTX6It2px5sw2aW/QFaLHO1SClakXKvUHL4/xyo/gVHzDQ1M9JD9mKQAJ6vynb', '', '', null, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDtJvawWjhQhI+J3EnD3gvuh+t1zB4bOMW9PJUdk27YQDyiGVd42QdHLofdTN1yXKXYZR1Bmy4W1pZhucSoDdS7fGfkKHm3zRMsijNOiPWHg0spMEchI4YTlIC43iFVdzSPE/2sIZfrW/9MspXfuWqFySsTsf6c6qJc6A0bNKJhMwIDAQAB', null, '11', null);
INSERT INTO `uchannel` VALUES ('24', '1', '10281191', '', 'MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK2yrlGlXXF6E+e2z8QHXB5cF+Eb434jGTf7H9AOOaSz4GceTSJakqKcrID6tntId4JbZXXhTNvMRubEXVwjlkhuH5kOLCN2dgaMzqyVtsKCf6YwR0KkRp84Cv84o9kyZDcmvPJvSNLOS+zGCzN7Iaxu7HPtauKlhGhGr3KrwWqdAgMBAAECgYAHgYEm5g5zqOLTUIMJ5YeFiFU/1QSvnrSoRqHJS9QR2fQIgLa0lVVg0YRiznK0QR1o9Kodve6kUN9/eVzPbno/9KUg3x2OYKLv7Sc3sDaLdM3u0PsTTOTmyKnLg7D8eTv12PP6N5vV5HvLLI8UCzyOvDleHBHp+Tc822scSe6aAQJBAO6Xo4LK1GRLF8oG9Ekd+AgvhAFo5V/uFNu3BxT2J99CxW4mZT+UCctbBWi18NQWo1XGXKS4AxAiA8E5kYQgW/UCQQC6Xvabbo1tKuG8WVyCRUufEZ04GUbaBcRvE/QHEXlwvbsKJAFKo6e3lF6nnYWyjzI/d5j2QYN1dKVdsHt0vxMJAkEAsSdspA+gNju/lSUmuyeCY+mL9VQChAEOAbnbi0fegRpd55Sgtt1fjFuwH3iAMaoBaw3W+gMbWx42dYEeN+GjBQJBAI7+C4IIGXSYASiM+6Br4HCEiDchla3z3NpI2eOOcbmhqN9H7sHAvQ7qRJGgF5N/sNLnRTIz49P7kmFG5gIWFFkCQDv4lcPjCffuW63qKn6exq6968v/J5IRYU0hBxV0OC6oFdZfI8QO/j8e46fwSLnsjGJDIzKRBp/8shY8N8BvNQM=', '', '900086000026798886', 'MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIjh2xE4Glq/P+B01rqHlozcPmBG3k85ySvNCJvdCaWKvDSVynTo9G1rCPEyV/6LtQ7pahVKZOBtPBKCPSo5NakCAwEAAQ==', 'MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAiOHbETgaWr8/4HTWuoeWjNw+YEbeTznJK80Im90JpYq8NJXKdOj0bWsI8TJX/ou1DulqFUpk4G08EoI9Kjk1qQIDAQABAkA8r+XwPG9yzFgFq8eH65VT3lHJXaIyfewy/zRR5i3gtnl9HQmQStCpWSZ8JfgGTJ75wdGHykAQAb1CXhJbEmYBAiEA3KzMh+0bYBi2nnvKmD126Gi2jUwigQeB8so6EaxwH2MCIQCey0C+LxhI4C2ChIUswZqsc7mHjkSUHAEpGu5UtvoigwIgXtp/x+VpIPM+e/zl5m51EA0CetXA9wlNGBlIwJQIVdUCIQCFzu5ylIrZNhcDaZozOb2V3jqTsN23FNYhhjEZL8sd8wIhAIpfSme5EBlLbADnWtNUhEQRk+v35OxkzB5B0xw2XUzg', '12', null);
INSERT INTO `uchannel` VALUES ('25', '1', '10679', 'bU5Ef4F1Ns792gDv', '', '', '5000128835', 'MUVEOUMwRDQ4RDhGRjNFOEJGQUM0RjRBQTQ5MDhGMDY5RTU3MkYxNE1UWTVOamsxT0RjNE1qRXpPVEU1TXpReU5EY3JNVGsyTkRJNU16RTFNREkyTURVek56WTBOVE0xTmpZd01ESXhNakUyTkRRM05qZzFNalk1', '', '13', null);
INSERT INTO `uchannel` VALUES ('26', '1', 'CPG54F58B76B11B2', '741b65253193b6592c55c5f9269f7338', '', '', '', '', '', '14', null);
INSERT INTO `uchannel` VALUES ('27', '1', '', '1378375366Az26xatNyDOD5EM6D2ys', 'ug2KMdLi2JSr4naOE48XmL3h', '', '', '', '', '15', null);
INSERT INTO `uchannel` VALUES ('28', '1', '', '42bc87ca29a772d0mBj6Pxj1GtUIDqPfZg3ubUjpWeefTB8RPjVThxu4cKa5sB0', '', '', '', '', '', '16', null);
INSERT INTO `uchannel` VALUES ('29', '1', '101185', '04USV8I2TKI1YCQB', '87W02FS46OE04DQBWJCPTD47GYFEIA5SXID4B0SH1FQV6SGUVTJISRYMAHNIXRZH', '', '', '', '', '17', null);
INSERT INTO `uchannel` VALUES ('30', '1', '1161', 'jzLdOuDM68K4u50n717MlQxg25OXWm2t', '', '', '', '', '', '18', null);
INSERT INTO `uchannel` VALUES ('31', '1', '2487115', 'ca6326a22f214721948bc8f0da33962d', 'xuaut7WRmKttyMnkx4G1Tn6ulFwPKjue', '', '', '', '', '19', null);
INSERT INTO `uchannel` VALUES ('32', '1', '5000001004', '11d97eb60960473e9de0839cbd7ae921', '', '', '', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDATVHffpz5SpCN7RAMtIb8VX54ikEWyU3wDoOwSFLeeIDAp1Mgw78RjYr1rPRclShMb/7rsmo4AMsQbbEmdmeVz3iCsgBJM65SelkTi/vmFERXxA6h3LYXUop31OzNz0XCVRk9sDlMAPQ9e1W5Sd8dV/0hRxGjkTvxF9XiKVq0AwIDAQAB', 'MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI36daGEKh8opX2chHkn0xj7zU+piIzU\r\nx9+QKqvc6+jBXwqBnWFdZLIS/ZiQ+T6ILeCyS5MSGMu6b6HK2qXi/5ISD7OJE48awAbu5NgRa3GGoZDO\r\nmXfC4P18VBY3DHUnstOqDOwgyEkVScxg7tXwys2Ca66KYmBvyCeFppy8ahSXAgMBAAECgYAQPT2HI97N\r\ngZCsNWLRmMSEbwiku/dqZEwYPh0jWzmAfd2pDfo6ULxbweBPuwVHKbxVnw8V21QKuWBbWSB5KOWiWpcf\r\nToZ1aNbultTrjBP4u46P/hym7tAvj6z2hA0oTMzy0c6FJ0taUQDfz5E+iZ2EDTddGq4mJGfW+CXaXjdS\r\nwQJBAPMHck9L3L/ehD9wJJelDgFL9jg+3MlNUcT87YW/QB0p42Uy1Q9ZrN2ElAr3cQ0hJuQ1C+hRSyOS\r\n/6DGoMSiihMCQQCVjlZX6OTsWtYXbhAPgwOjr16YG5gf207UJX7+ONdDLxCM7ym4nunvQpjDwwCFz/ry\r\nhPfON04J4s28AGkdJtvtAkALS59OfDIoYMk4wZeRzMQX31X0S0eru4I/a5oGi1sJf/bnrzMj4YEdUpom\r\n4FVKzO2n0adaBt09LAnhiRnTydItAkAUIAJIdCOSFqsmnf31SGkN9sRg7Z2r88QI0j7jkNyOwt+UR36y\r\nxFewhaKu/Set2g3n4rLCAjkzJV0QBnIepSMFAkEA4V4L9i/nLEDKFSrCNtE1n3ff742X/XpiQF4qwVhZ\r\nW8WpbYyuneHqk7sctUjyHQyFvWfKpXB5cv2MTTocq5rn+Q==', '20', '1');
INSERT INTO `uchannel` VALUES ('33', '1', 'b32a1d1fd1826545', '34fe34225d1ed6b0', 'c3657cdd837eb9dd', '', '', '', '', '21', null);
INSERT INTO `uchannel` VALUES ('34', '1', 'b32a1d1fd1826545', '23F28F98461143E7A75F96327B695734', 'BCD61AE8337C4270A3D7E597102DFD66', '', '', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSGer0HsTaWI12og+uRGP+XfGVaMVqK5c6/90eLT3DHSdiF6SpqbR5BmxfpEyOlwwMdVSHMw+BqqE1PiKH4fx1ZTV0/JFc33Y55PJ9jVZ5TKHKQqvr/hn12DNgD8ntmPCLkJwv0mD3X4Pu3V2Qo7lDbJDX/XXd91/wJDCq3GToAwIDAQAB', 'MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMHhEdFSgO0MDcWP0itaGMwXMJOYei5LMDkpoWdm3zreCd/y49op+73Sv6lTIb2HX2D/AvDxOjNq93L9su6c8q8y+tUs88BGCS3FHRjwHqKtInuE2AkLLPKMp8LNKSAyqOzFOBC3CHF6wMRzbzXYTQWwKkBCWb2B1wzqbEjPKqcvAgMBAAECgYAN/WikuxhUgo8n11XqDOlHKNE3hUQjvQcwyME9zd2DyOvbfhJU9ryUmPV9iWMg4vjN7fjPXAjAFxLd+FKGB0s3mvwpHSOur+SX54apbQN2eYu1Ps3aUU39s/XaDp0XKInmjp9feAKt2p/oju1CKUh2m81osFqftOw0IhdpO8w+wQJBAOoa9v4RXtSO0rCjIohkvvbWXap6lSz//OqJmcJmYWoJwQYpvAb2gvZusw+AuWzkkY122EH9isSf0zAoApTbg88CQQDUAv7dJyPqc9EIGmsiw+6Xs6Hbtoz6QSaubitmE7AiKARBFvzA1/YiIl2sluRvmL9CXkfpqTpJzob2AFGHzZ6hAkEAnHOE2WqWa4s/dtivPWPG04OTeVkO1NIHHl7zKR3uwETggNPnWufnwfVdKWEnioR+WYIxKHjfAmSlaKt0fjLttQJAcSZ8IUm/aGSRfjKjVTWexAYR73I5QWK+I0AXF26QhVo4EwkSslVQPp16wx7xIgIyqVgqJzGzMyrvaqF24fTCoQJAeRuF4M8c6TQrE7ELenwPbYpYOEq+ovMJrSS2d06JYL4u0+SA6Xvfa1+q3brt5XlM2MTxdiWHXdOjYbKaTvDdew==', '22', null);
INSERT INTO `uchannel` VALUES ('1000', '1', '1', '58C6A68DDDEE471AA43266E427F38D92', '', '', '', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2kcrRvxURhFijDoPpqZ/IgPlAgppkKrek6wSrua1zBiGTwHI2f+YCa5vC1JEiIi9uw4srS0OSCB6kY3bP2DGJagBoEgj/rYAGjtYJxJrEiTxVs5/GfPuQBYmU0XAtPXFzciZy446VPJLHMPnmTALmIOR5Dddd1Zklod9IQBMjjwIDAQAB', '', '23', null);

-- ----------------------------
-- Table structure for `uchannelmaster`
-- ----------------------------
DROP TABLE IF EXISTS `uchannelmaster`;
CREATE TABLE `uchannelmaster` (
  `masterID` int(11) NOT NULL,
  `authUrl` varchar(1024) DEFAULT NULL,
  `masterName` varchar(255) DEFAULT NULL,
  `sdkName` varchar(255) DEFAULT NULL,
  `nameSuffix` varchar(255) DEFAULT NULL,
  `payCallbackUrl` varchar(1024) DEFAULT NULL,
  `verifyClass` varchar(1024) DEFAULT NULL,
  `orderUrl` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`masterID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uchannelmaster
-- ----------------------------
INSERT INTO `uchannelmaster` VALUES ('1', 'http://sdk.g.uc.cn/cp/account.verifySession', 'UC', 'uc', '.uc', 'http://localhost:8080/uc/payCallback', 'com.u8.server.sdk.uc.UCSDK', null);
INSERT INTO `uchannelmaster` VALUES ('2', 'http://ngsdk.d.cn/api/cp/checkToken', '当乐', 'downjoy', '.dl', 'http://localhost:8080/downjoy/payCallback', 'com.u8.server.sdk.downjoy.DownjoySDK', null);
INSERT INTO `uchannelmaster` VALUES ('3', 'https://openapi.360.cn/user/me', '360', 'qihoo360', '.qihoo', 'http://192.168.3.32:8080/qihoo360/payCallback', 'com.u8.server.sdk.qihoo360.Qihoo360SDK', null);
INSERT INTO `uchannelmaster` VALUES ('4', 'http://querysdkapi.91.com/CpLoginStateQuery.ashx', '百度', 'baidu', '.baidu', 'http://localhost:8080/baidu/payCallback', 'com.u8.server.sdk.baidu.BaiduSDK', '');
INSERT INTO `uchannelmaster` VALUES ('5', 'http://mis.migc.xiaomi.com/api/biz/service/verifySession.do', '小米', 'xiaomi', '.xiaomi', 'http://localhost:8080/xiaomi/payCallback', 'com.u8.server.sdk.xiaomi.XiaoMiSDK', '');
INSERT INTO `uchannelmaster` VALUES ('6', 'http://m.4399api.com/openapi/oauth-check.html', '4399', 'm4399', '.m4399', 'http://localhost:8080/m4399/payCallback', 'com.u8.server.sdk.m4399.M4399SDK', '');
INSERT INTO `uchannelmaster` VALUES ('7', '', 'oppo', 'oppo', '.oppo', 'http://localhost:8080/oppo/payCallback', 'com.u8.server.sdk.oppo.OppoSDK', '');
INSERT INTO `uchannelmaster` VALUES ('8', 'https://usrsys.inner.bbk.com/auth/user/info', 'ViVo', 'vivo', '.vivo', 'http://localhost:8080/vivo/payCallback', 'com.u8.server.sdk.vivo.VivoSDK', 'https://pay.vivo.com.cn/vcoin/trade');
INSERT INTO `uchannelmaster` VALUES ('9', 'http://sdk.muzhiwan.com/oauth2/getuser.php', '拇指玩', 'muzhiwan', '.mzw', 'http://localhost:8080/muzhiwan/payCallback', 'com.u8.server.sdk.muzhiwan.MuZhiWanSDK', '');
INSERT INTO `uchannelmaster` VALUES ('10', 'https://pay.wandoujia.com/api/uid/check', '豌豆荚', 'wandoujia', '.wdj', 'http://localhost:8080/wandoujia/payCallback', 'com.u8.server.sdk.wandoujia.WanDouJiaSDK', '');
INSERT INTO `uchannelmaster` VALUES ('11', 'http://passport.lenovo.com/interserver/authen/1.2/getaccountid', '联想', 'lenovo', '.lenovo', 'http://localhost:8080/lenovo/payCallback', 'com.u8.server.sdk.lenovo.LenovoSDK', '');
INSERT INTO `uchannelmaster` VALUES ('12', 'https://api.vmall.com/rest.php', '华为', 'huawei', '.huawei', 'http://localhost:8080/huawei/payCallback', 'com.u8.server.sdk.huawei.HuaWeiSDK', '');
INSERT INTO `uchannelmaster` VALUES ('13', 'http://api.appchina.com/appchina-usersdk/user/get.json', '应用汇', 'appchina', '.appchina', 'http://localhost:8080/appchina/payCallback', 'com.u8.server.sdk.appchina.AppChinaSDK', '');
INSERT INTO `uchannelmaster` VALUES ('14', 'http://server.cpo2o.com/Verify/login.html', '友游', 'cloudpoint', '.cloudpoint', 'http://localhost:8080/cloudpoint/payCallback', 'com.u8.server.sdk.cloudpoint.CloudPointSDK', '');
INSERT INTO `uchannelmaster` VALUES ('15', 'http://user.anzhi.com/web/api/sdk/third/1/queryislogin', '安智', 'anzhi', '.anzhi', 'http://localhost:8080/anzhi/payCallback', 'com.u8.server.sdk.anzhi.AnzhiSDK', '');
INSERT INTO `uchannelmaster` VALUES ('16', 'http://pay.mumayi.com/user/index/validation', '木蚂蚁', 'mumayi', '.mumayi', 'http://localhost:8080/mumayi/payCallback', 'com.u8.server.sdk.mumayi.MuMaYiSDK', '');
INSERT INTO `uchannelmaster` VALUES ('17', 'http://guopan.cn/gamesdk/verify', '叉叉助手', 'guopan', '.guopan', 'http://localhost:8080/guopan/payCallback', 'com.u8.server.sdk.guopan.GuoPanSDK', '');
INSERT INTO `uchannelmaster` VALUES ('18', 'http://ng.sdk.paojiao.cn/api/user/token.do', '泡椒', 'paojiao', '.paojiao', 'http://localhost:8080/paojiao/payCallback', 'com.u8.server.sdk.paojiao.PaoJiaoSDK', '');
INSERT INTO `uchannelmaster` VALUES ('19', 'https://api.game.meizu.com/game/security/checksession', '魅族', 'meizu', '.meizu', 'http://localhost:8080/meizu/payCallback', 'com.u8.server.sdk.meizu.MeizuSDK', '');
INSERT INTO `uchannelmaster` VALUES ('20', 'https://openapi.coolyun.com/oauth2/token', '酷派', 'coolpad', '.coolpad', 'http://localhost:8080/coolpad/payCallback', 'com.u8.server.sdk.coolpad.CoolPadSDK', 'http://pay.coolyun.com:6988/payapi/order');
INSERT INTO `uchannelmaster` VALUES ('21', '', '偶玩', 'ouwan', '.ouwan', 'http://localhost:8080/ouwan/payCallback', 'com.u8.server.sdk.ouwan.OuWanSDK', '');
INSERT INTO `uchannelmaster` VALUES ('22', 'https://id.gionee.com/account/verify.do', '金立', 'jinli', '.am', 'http://localhost:8080/jinli/payCallback', 'com.u8.server.sdk.jinli.JinLiSDK', 'https://pay.gionee.com/order/create');
INSERT INTO `uchannelmaster` VALUES ('23', 'https://pay.slooti.com/?r=auth/verify', 'Itools', 'itools', '.itools', 'http://localhost:8080/itools/payCallback', 'com.u8.server.sdk.itools.ItoolsSDK', '');

-- ----------------------------
-- Table structure for `ugame`
-- ----------------------------
DROP TABLE IF EXISTS `ugame`;
CREATE TABLE `ugame` (
  `appID` int(11) NOT NULL,
  `appkey` varchar(255) DEFAULT NULL,
  `appRSAPubKey` varchar(1024) DEFAULT NULL,
  `appRSAPriKey` varchar(1024) DEFAULT NULL,
  `createTime` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `payCallback` varchar(255) DEFAULT NULL,
  `payCallbackDebug` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`appID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ugame
-- ----------------------------
INSERT INTO `ugame` VALUES ('1', 'f32fdc02123a82524eb4ea95e1383d0b', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDtJvawWjhQhI+J3EnD3gvuh+t1zB4bOMW9PJUdk27YQDyiGVd42QdHLofdTN1yXKXYZR1Bmy4W1pZhucSoDdS7fGfkKHm3zRMsijNOiPWHg0spMEchI4YTlIC43iFVdzSPE/2sIZfrW/9MspXfuWqFySsTsf6c6qJc6A0bNKJhMwIDAQAB', 'MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAO0m9rBaOFCEj4ncScPeC+6H63XMHhs4xb08lR2TbthAPKIZV3jZB0cuh91M3XJcpdhlHUGbLhbWlmG5xKgN1Lt8Z+QoebfNEyyKM06I9YeDSykwRyEjhhOUgLjeIVV3NI8T/awhl+tb/0yyld+5aoXJKxOx/pzqolzoDRs0omEzAgMBAAECgYBGzwt5PHb0E6CIGS4tPW9ymULEuV2D4z+ncR9U5WCDUSrJe6eSfbqellYazYiRTPh31DkYDa2FRC1CoKUHSJnrjeNR2TMw0WUBFvNcqYe2qOJZg3iOhyUDhIChhQiWWC9VrzAvqSU6tuyKGMy5rAWbfTneEnL7NHsTgRRDC+0JAQJBAPlRGW6T4TnRBtbOpRcMU+jdCyJAK3zwuRO13alhexDLq105D1osg2uP1d3+XvTQudwCGo1qRfBSp/W72fynz5kCQQDzgmLyxGzO1rugtJNMLQTqsRGg8ZUoUPmsEVGbmnHwRzd2OGHWbT1JuIEEb+ivrZV3PfeEObv7fDAT6qIhyiarAkAcd4ka2iG+U0KfpkqtXgf6r7qEt6T/iBDp0js0CuBdY5P2efxpxGlhD7RQu6ml9Gs0Vr0nZnoD3bw1z7QtKBAJAkBiqBjesqZCxs0NtxtWaYbsbwDta/M6elQtWnbtzA0NhEz8IKvC7E9AZvgejBiB1JoRzZFSiPGYWiBAcXduqTAxAkEAqG24ePhjesKoF1Us2ViqgJC7zDd96v+LI5eausw3TfKjO4jj5oMoQiyc+hZFxHYlkyZRfA6XEraF1Rdgngf65w==', '0', '测试游戏', '', '');

-- ----------------------------
-- Table structure for `uorder`
-- ----------------------------
DROP TABLE IF EXISTS `uorder`;
CREATE TABLE `uorder` (
  `orderID` bigint(20) NOT NULL,
  `appID` int(11) NOT NULL,
  `channelID` int(11) NOT NULL,
  `channelOrderID` varchar(255) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `extension` varchar(255) DEFAULT NULL,
  `money` int(11) NOT NULL,
  `state` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `roleID` varchar(255) DEFAULT NULL,
  `roleName` varchar(255) DEFAULT NULL,
  `serverID` varchar(255) DEFAULT NULL,
  `serverName` varchar(255) DEFAULT NULL,
  `createdTime` datetime DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  `productDesc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uorder
-- ----------------------------
INSERT INTO `uorder` VALUES ('654898395705507841', '1', '503', '', 'RMB', null, '100', '1', '3', '1', '?????', '1', '??', null, null, null);
INSERT INTO `uorder` VALUES ('654898640518643714', '1', '503', '', 'RMB', null, '100', '1', '3', '1', '?????', '1', '??', null, null, null);
INSERT INTO `uorder` VALUES ('654901505261830147', '1', '503', '', 'RMB', null, '100', '1', '3', '1', '?????', '1', '??', null, null, null);
INSERT INTO `uorder` VALUES ('654901677060521988', '1', '503', '', 'RMB', null, '100', '1', '3', '1', '?????', '1', '??', null, null, null);
INSERT INTO `uorder` VALUES ('654906289855397889', '1', '503', '', 'RMB', null, '100', '1', '3', '1', '?????', '1', '??', null, null, null);
INSERT INTO `uorder` VALUES ('654907385072058370', '1', '503', '', 'RMB', null, '100', '1', '3', '1', '?????', '1', '??', null, null, null);
INSERT INTO `uorder` VALUES ('654907569755652099', '1', '503', '', 'RMB', null, '100', '1', '3', '1', '?????', '1', '??', null, null, null);
INSERT INTO `uorder` VALUES ('654914501832867844', '1', '503', '', 'RMB', null, '100', '1', '3', '1', '测试角色名', '1', '测试', null, null, null);
INSERT INTO `uorder` VALUES ('656016779419582465', '1', '503', '', 'RMB', '1428891376228', '100', '1', '3', '1', '测试角色名', '1', '测试', null, '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('656068817243340801', '1', '503', '', 'RMB', '1428902015786', '100', '1', '3', '1', '测试角色名', '1', '测试', null, '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('656069040581640194', '1', '503', '656069040581640194', 'RMB', '1428902064311', '10000', '2', '3', '1', '测试角色名', '1', '测试', null, '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('658356789271592961', '1', '11', '', 'RMB', '1429255035989', '100', '1', '4', '1', '测试角色名', '1', '测试', null, '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('658896421847564289', '1', '503', '', 'RMB', '1429336831312', '100', '1', '5', '1', '测试角色名', '1', '测试', null, '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('659988610556100609', '1', '11', '11342423', 'RMB', '1429502514494', '10000', '2', '4', '1', '测试角色名', '1', '测试', null, '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('660073230001766401', '1', '16', '', 'RMB', '1429520025537', '100', '1', '7', '1', '测试角色名', '1', '测试', null, '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('660076983803183106', '1', '16', '', 'RMB', '1429520601543', '100', '1', '2', '1', '测试角色名', '1', '测试', null, '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('660081308835250177', '1', '16', '', 'RMB', '1429521547973', '100', '1', '2', '1', '测试角色名', '1', '测试', null, '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('660588278184935425', '1', '12', '235234554', 'RMB', '1429596446912', '100', '4', '9', '1', '测试角色名', '1', '测试', null, '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('660707025440735233', '1', '17', '8908009089', 'RMB', '1429620926766', '10000', '2', '10', '1', '测试角色名', '1', '测试', null, '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('661185128315224065', '1', '18', '34543545', 'RMB', '1429689773941', '100', '2', '11', '1', '测试角色名', '1', '测试', null, '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('661636280269930497', '1', '19', '43253454354', 'RMB', '1429753452230', '10000', '2', '12', '1', '测试角色名', '1', '测试', null, '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('661646034140659713', '1', '19', '', 'RMB', '1429755340542', '100', '1', '12', '1', '测试角色名', '1', '测试', null, '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662260334723072001', '1', '20', '', 'RMB', '1429852229817', '100', '1', '14', '1', '测试角色名', '1', '测试', null, '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662260944608428033', '1', '20', '', 'RMB', '1429852363141', '100', '1', '14', '1', '测试角色名', '1', '测试', '2015-04-24 13:11:18', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662262684070182913', '1', '20', '', 'RMB', '1429852744334', '100', '1', '14', '1', '测试角色名', '1', '测试', '2015-04-24 13:17:39', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662263066322272258', '1', '20', '', 'RMB', '1429852826621', '100', '1', '14', '1', '测试角色名', '1', '测试', '2015-04-24 13:19:00', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662263439984427011', '1', '20', '', 'RMB', '1429852909172', '100', '1', '14', '1', '测试角色名', '1', '测试', '2015-04-24 13:20:23', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662265226690822148', '1', '20', '', 'RMB', '1429853300922', '100', '1', '14', '1', '测试角色名', '1', '测试', '2015-04-24 13:26:55', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662265982605066241', '1', '20', '', 'RMB', '1429853464556', '100', '1', '14', '1', '测试角色名', '1', '测试', '2015-04-24 13:29:39', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662266193058463746', '1', '20', '', 'RMB', '1429853509977', '100', '1', '14', '1', '测试角色名', '1', '测试', '2015-04-24 13:30:24', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662267039167021059', '1', '20', '', 'RMB', '1429853694257', '100', '1', '14', '1', '测试角色名', '1', '测试', '2015-04-24 13:33:29', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662267941110153220', '1', '20', '', 'RMB', '1429853893283', '100', '1', '14', '1', '测试角色名', '1', '测试', '2015-04-24 13:36:47', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662269891025305605', '1', '20', '', 'RMB', '1429854318860', '100', '1', '14', '1', '测试角色名', '1', '测试', '2015-04-24 13:43:53', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662270006989422598', '1', '20', '', 'RMB', '1429854341767', '100', '1', '14', '1', '测试角色名', '1', '测试', '2015-04-24 13:44:16', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662286259145670663', '1', '20', '34544345435', 'RMB', '1429857650169', '10000', '2', '14', '1', '测试角色名', '1', '测试', '2015-04-24 14:39:24', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662286332160114696', '1', '20', '', 'RMB', '1429857667094', '10000', '1', '14', '1', '测试角色名', '1', '测试', '2015-04-24 14:39:41', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662294917799739393', '1', '20', '', 'RMB', '1429859302535', '10000', '1', '14', '1', '测试角色名', '1', '测试', '2015-04-24 15:06:56', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662294986519216130', '1', '20', '', 'RMB', '1429859314687', '10000', '1', '14', '1', '测试角色名', '1', '测试', '2015-04-24 15:07:08', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662295033763856387', '1', '20', '', 'RMB', '1429859325232', '10000', '1', '14', '1', '测试角色名', '1', '测试', '2015-04-24 15:07:19', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662295072418562052', '1', '20', '34544345435', 'RMB', '1429859334392', '10000', '2', '14', '1', '测试角色名', '1', '测试', '2015-04-24 15:07:28', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662773862487818241', '1', '20', '', 'RMB', '1429928565714', '10000', '1', '15', '1', '测试角色名', '1', '测试', '2015-04-25 10:21:17', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662775855352643586', '1', '20', '', 'RMB', '1429929001823', '10000', '1', '15', '1', '测试角色名', '1', '测试', '2015-04-25 10:28:33', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662795139755802625', '1', '21', '23453454354', 'RMB', '1429932972301', '10000', '2', '16', '1', '测试角色名', '1', '测试', '2015-04-25 11:34:43', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('662849492566933505', '1', '22', '345435435', 'RMB', '1429944114939', '10000', '2', '17', '1', '测试角色名', '1', '测试', '2015-04-25 14:40:26', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('663986842856587265', '1', '23', '2354325345', 'RMB', '1430119175223', '10000', '2', '18', '1', '测试角色名', '1', '测试', '2015-04-27 15:18:04', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('664070427215134721', '1', '24', '', 'RMB', '1430136220108', '10000', '1', '19', '1', '测试角色名', '1', '测试', '2015-04-27 20:02:09', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('664071522431795201', '1', '24', '', 'RMB', '1430136458764', '10000', '1', '19', '1', '测试角色名', '1', '测试', '2015-04-27 20:06:08', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('664071668460683266', '1', '24', '', 'RMB', '1430136493363', '10000', '1', '19', '1', '测试角色名', '1', '测试', '2015-04-27 20:06:42', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('664074159541714947', '1', '24', '', 'RMB', '1430137035192', '10000', '1', '19', '1', '测试角色名', '1', '测试', '2015-04-27 20:15:46', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('664074636283084804', '1', '24', '', 'RMB', '1430137138234', '10000', '1', '19', '1', '测试角色名', '1', '测试', '2015-04-27 20:17:29', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('664078991379922949', '1', '24', '', 'RMB', '1430138088360', '10000', '1', '19', '1', '测试角色名', '1', '测试', '2015-04-27 20:33:19', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('664444544636420097', '1', '24', '', 'RMB', '1430184044385', '10000', '1', '19', '1', '测试角色名', '1', '测试', '2015-04-28 09:19:11', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('664445433694650370', '1', '24', '', 'RMB', '1430184239937', '10000', '1', '19', '1', '测试角色名', '1', '测试', '2015-04-28 09:22:26', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('664446984177844227', '1', '24', '', 'RMB', '1430184576560', '10000', '1', '19', '1', '测试角色名', '1', '测试', '2015-04-28 09:28:03', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('664451287735074817', '1', '24', '', 'RMB', '1430185518777', '10000', '1', '19', '1', '测试角色名', '1', '测试', '2015-04-28 09:43:45', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('664551566631501825', '1', '25', '', 'RMB', '1430205967061', '10000', '1', '20', '1', '测试角色名', '1', '测试', '2015-04-28 15:24:33', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('664646085976784897', '1', '26', '345435345', 'RMB', '1430225395635', '10000', '2', '21', '1', '测试角色名', '1', '测试', '2015-04-28 20:48:24', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('664650990829436930', '1', '16', '', 'RMB', '1430226225721', '10000', '1', '2', '1', '测试角色名', '1', '测试', '2015-04-28 21:02:14', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('664654864889937923', '1', '10', '', 'RMB', '1430227072591', '10000', '1', '22', '1', '测试角色名', '1', '测试', '2015-04-28 21:16:20', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('665655343161802753', '1', '27', '', 'RMB', '1430374179468', '10000', '1', '23', '1', '测试角色名', '1', '测试', '2015-04-30 14:08:02', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('665674460061237249', '1', '28', '', 'RMB', '1430378112507', '10000', '1', '24', '1', '测试角色名', '1', '测试', '2015-04-30 15:13:37', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('665702862679965697', '1', '30', '', 'RMB', '1430384074782', '10000', '1', '25', '1', '测试角色名', '1', '测试', '2015-04-30 16:52:58', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('668974012851814401', '1', '30', '', 'RMB', '1430707860967', '10000', '1', '25', '1', '测试角色名', '1', '测试', '2015-05-04 10:49:18', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('668992266462822401', '1', '29', '', 'RMB', '1430711607352', '10000', '1', '26', '1', '测试角色名', '1', '测试', '2015-05-04 11:51:44', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('669055462611615745', '1', '31', '', 'RMB', '1430724441428', '10000', '1', '27', '1', '测试角色名', '1', '测试', '2015-05-04 15:25:38', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('669154328463802369', '1', '33', '', 'RMB', '1430744577958', '10000', '1', '28', '1', '测试角色名', '1', '测试', '2015-05-04 21:01:17', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('669509243086307329', '1', '33', '', 'RMB', '1430788450844', '10000', '1', '28', '1', '测试角色名', '1', '测试', '2015-05-05 09:12:28', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('669509320395718658', '1', '33', '', 'RMB', '1430788467997', '10000', '1', '28', '1', '测试角色名', '1', '测试', '2015-05-05 09:12:46', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('669509749892448259', '1', '33', '', 'RMB', '1430788560801', '10000', '1', '28', '1', '测试角色名', '1', '测试', '2015-05-05 09:14:18', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('670082350637383681', '1', '34', '', 'RMB', '1430877071806', '10000', '1', '29', '1', '测试角色名', '1', '测试', '2015-05-06 09:49:25', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('670082397882023938', '1', '34', '', 'RMB', '1430877083005', '10000', '1', '29', '1', '测试角色名', '1', '测试', '2015-05-06 09:49:36', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('670086774453698561', '1', '34', '', 'RMB', '1430877797785', '10000', '1', '29', '1', '测试角色名', '1', '测试', '2015-05-06 10:01:31', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('670097932778733569', '1', '34', '', 'RMB', '1430880232098', '10000', '1', '29', '1', '测试角色名', '1', '测试', '2015-05-06 10:42:05', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('670100526938980353', '1', '34', '', 'RMB', '1430880799686', '10000', '1', '29', '1', '测试角色名', '1', '测试', '2015-05-06 10:51:33', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('670101510486491137', '1', '34', '', 'RMB', '1430881013318', '10000', '1', '29', '1', '测试角色名', '1', '测试', '2015-05-06 10:55:06', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('670106737461690370', '1', '32', '', 'RMB', '1430881914216', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-06 11:10:07', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('670114107625570305', '1', '32', '', 'RMB', '1430883525743', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-06 11:36:59', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('670115159892557825', '1', '32', '', 'RMB', '1430883754639', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-06 11:40:48', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('670117324556075009', '1', '32', '', 'RMB', '1430884227289', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-06 11:48:40', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('670144438684614657', '1', '32', '', 'RMB', '1430889663280', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-06 13:19:17', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('670145864613756930', '1', '32', '', 'RMB', '1430889975160', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-06 13:24:29', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('670146182441336835', '1', '32', '', 'RMB', '1430890044859', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-06 13:25:39', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('670148896860667908', '1', '32', '', 'RMB', '1430890637438', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-06 13:35:31', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('670150112336412673', '1', '32', '', 'RMB', '1430890903744', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-06 13:39:58', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('670152216870387713', '1', '32', '', 'RMB', '1430891361948', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-06 13:47:36', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('670154768080961537', '1', '32', '', 'RMB', '1430891920585', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-06 13:56:54', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('670155420915990529', '1', '32', '', 'RMB', '1430892060400', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-06 13:59:14', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('672890219866882049', '1', '32', '', 'RMB', '1431307580742', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-11 09:24:23', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('672893509811830785', '1', '32', '', 'RMB', '1431308298532', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-11 09:36:21', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('672895425367244801', '1', '32', '', 'RMB', '1431308716741', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-11 09:43:19', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('672897212073639937', '1', '32', '', 'RMB', '1431309109066', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-11 09:49:51', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('672898341650038785', '1', '32', '', 'RMB', '1431309355940', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-11 09:53:58', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('672906691066462209', '1', '32', '', 'RMB', '1431310936517', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-11 10:20:18', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('672910311723892737', '1', '32', '', 'RMB', '1431311727630', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-11 10:33:29', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('672915027597983745', '1', '32', '', 'RMB', '1431312758564', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-11 10:50:39', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('672916273138499585', '1', '32', '', 'RMB', '1431313028463', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-11 10:55:09', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('672916956038299649', '1', '32', '', 'RMB', '1431313179550', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-11 10:57:40', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('672918987557830657', '1', '32', '', 'RMB', '1431313380135', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-11 11:01:01', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('672919979695276033', '1', '32', '', 'RMB', '1431313600119', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-11 11:04:40', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('672920976127688705', '1', '32', '', 'RMB', '1431313815855', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-11 11:08:16', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('672921521588535297', '1', '32', '', 'RMB', '1431313934537', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-11 11:10:15', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('672924356266950658', '1', '32', '', 'RMB', '1431314555043', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-11 11:20:35', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('673003748737417219', '1', '32', '', 'RMB', '1431330920185', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-11 15:53:24', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('673004341442904068', '1', '32', '', 'RMB', '1431331049136', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-11 15:55:34', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('673007463884128257', '1', '32', '', 'RMB', '1431331493631', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-11 16:02:57', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('673008649295101954', '1', '32', '', 'RMB', '1431331749444', '10000', '1', '30', '1', '测试角色名', '1', '测试', '2015-05-11 16:07:13', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('694418605999128577', '1', '16', '', 'RMB', '1434532104099', '10000', '1', '2', '1', '测试角色名', '1', '测试', '2015-06-17 17:08:11', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('694423205909102594', '1', '12', '', 'RMB', '1434533112024', '10000', '1', '32', '1', '测试角色名', '1', '测试', '2015-06-17 17:24:58', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('694423343348056067', '1', '12', '', 'RMB', '1434533139501', '10000', '1', '32', '1', '测试角色名', '1', '测试', '2015-06-17 17:25:26', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('694423467902107652', '1', '12', '', 'RMB', '1434533169429', '10000', '1', '33', '1', '测试角色名', '1', '测试', '2015-06-17 17:25:55', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('694429987662462981', '1', '17', '', 'RMB', '1434534590449', '10000', '1', '10', '1', '测试角色名', '1', '测试', '2015-06-17 17:49:37', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('694876041490989057', '1', '24', '', 'RMB', '1434596914978', '10000', '1', '34', '1', '测试角色名', '1', '测试', '2015-06-18 11:08:20', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('694876170340007938', '1', '24', '', 'RMB', '1434596945965', '10000', '1', '34', '1', '测试角色名', '1', '测试', '2015-06-18 11:08:50', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('694876389383340035', '1', '24', '', 'RMB', '1434596993520', '10000', '1', '34', '1', '测试角色名', '1', '测试', '2015-06-18 11:09:37', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('694880757365080068', '1', '24', '', 'RMB', '1434597945905', '10000', '1', '34', '1', '测试角色名', '1', '测试', '2015-06-18 11:25:30', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('701798296116527105', '1', '10', '', 'RMB', '1435668001774', '10000', '1', '22', '1', '测试角色名', '1', '测试', '2015-06-30 20:39:21', '元宝', '购买100元宝');
INSERT INTO `uorder` VALUES ('706207367808679937', '1', '12', '', 'RMB', '1436166980543', '10000', '1', '33', '1', '测试角色名', '1', '测试', '2015-07-06 15:15:28', '元宝', '购买100元宝');

-- ----------------------------
-- Table structure for `uuser`
-- ----------------------------
DROP TABLE IF EXISTS `uuser`;
CREATE TABLE `uuser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appID` int(11) NOT NULL,
  `channelID` int(11) NOT NULL,
  `channelUserID` varchar(255) DEFAULT NULL,
  `channelUserName` varchar(255) DEFAULT NULL,
  `channelUserNick` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uuser
-- ----------------------------
INSERT INTO `uuser` VALUES ('1', '1', '16', null, null, null, '2015-02-28 11:23:50', '2015-02-28 11:23:50', '1425093830573.qihoo', '1262599d7f5e71544d4956e7b1406e24');
INSERT INTO `uuser` VALUES ('2', '1', '16', '746123316', 'chenjie115521', '哈哈哈哇身上', '2015-02-28 13:36:46', '2015-06-17 17:08:04', '1425101806170.qihoo', '6c98ab40327812bf8effc075901f670b');
INSERT INTO `uuser` VALUES ('3', '1', '503', '1347463825', '1347463825', '九游玩家743863934', '2015-04-10 11:07:02', '2015-04-20 17:16:25', '1428635222022.uc', '973c5f13242e0fde7fbaefa4dc8d899b');
INSERT INTO `uuser` VALUES ('4', '1', '11', '0', null, null, '2015-04-17 15:15:48', '2015-04-20 17:06:56', '1429254948678.dl', '901fe8d32e5978fb104a44b68d9667b4');
INSERT INTO `uuser` VALUES ('5', '1', '503', '1999722123', '1999722123', '九游玩家602532480', '2015-04-17 17:32:29', '2015-04-18 13:59:03', '1429263149059.uc', '674c88acb1de07c33ae9b4100bb69334');
INSERT INTO `uuser` VALUES ('6', '1', '503', '1630153510', '1630153510', '九游玩家602972457', '2015-04-18 14:00:02', '2015-04-18 14:00:17', '1429336802222.uc', '21e15ab5b0b5efaf3837164167af0eaa');
INSERT INTO `uuser` VALUES ('7', '1', '16', '1460968533', 'test99009', '', '2015-04-20 16:52:18', '2015-04-20 16:52:18', '1429519938126.qihoo', 'b82d72961c6ce2928ead64cdd759918d');
INSERT INTO `uuser` VALUES ('8', '1', '12', '0', '', '', '2015-04-21 10:48:13', '2015-04-21 11:03:18', '1429584493936.baidu', 'a70fb4981c8f82db22a6ebd37d8bf423');
INSERT INTO `uuser` VALUES ('9', '1', '12', '644294698', '', '', '2015-04-21 14:05:08', '2015-04-21 14:05:08', '1429596308390.baidu', '824e97660e1afc9c8f9a145aa61d59f7');
INSERT INTO `uuser` VALUES ('10', '1', '17', '9516133', '', '', '2015-04-21 20:50:37', '2015-06-17 17:49:31', '1429620637444.xiaomi', '248a88c9e652d8c664692330f1ec6be1');
INSERT INTO `uuser` VALUES ('11', '1', '18', '1580331340', '894664603', '', '2015-04-22 15:32:17', '2015-04-22 15:34:02', '1429687937425.m4399', 'c14338360058dff4c0650609f942d79a');
INSERT INTO `uuser` VALUES ('12', '1', '19', '71798862', 'NM71798862', 'MI371798862', '2015-04-23 09:42:30', '2015-04-23 10:14:07', '1429753350836.oppo', '9fa1be662ac61b615f9b84efd1f1bbaf');
INSERT INTO `uuser` VALUES ('13', '1', '19', '78094833', 'NM78094833', '', '2015-04-23 10:39:35', '2015-04-23 10:40:02', '1429756775598.oppo', 'c55a18e0004b5bad46bb7eeb7988f7ca');
INSERT INTO `uuser` VALUES ('14', '1', '20', 'f342eecf444db1f0', 'MI364081467oi', '', '2015-04-24 13:08:11', '2015-04-24 15:06:51', '1429852091204.vivo', '1ca188c5c3eef45a9cbab42570beeadd');
INSERT INTO `uuser` VALUES ('15', '1', '20', 'ca6146091ac58fda', 'TianTian0220946lzf', '', '2015-04-25 10:19:32', '2015-04-25 10:27:33', '1429928372968.vivo', 'd9e1c7a379f28ef75b71130a02e7666b');
INSERT INTO `uuser` VALUES ('16', '1', '21', '8714739', 'dev_553b0b9e418bc', '', '2015-04-25 11:34:31', '2015-04-25 11:34:31', '1429932871370.mzw', '7ce4e5817f058ff0db4b7b6a33f4ba88');
INSERT INTO `uuser` VALUES ('17', '1', '22', '110734509', '', '', '2015-04-25 14:40:09', '2015-04-25 14:40:09', '1429944009342.wdj', '1805717266464386ceabd44ed12b806f');
INSERT INTO `uuser` VALUES ('18', '1', '23', '10041284767', '18202116067', '', '2015-04-27 14:26:32', '2015-04-27 14:26:32', '1430115992021.lenovo', '3273f8d42707860b246d6a0b09706b26');
INSERT INTO `uuser` VALUES ('19', '1', '24', '27385150', '7****7244@qq.com', '', '2015-04-27 20:01:59', '2015-04-28 09:43:37', '1430136119062.huawei', 'a7b7d464adf34121a76859417bec4493');
INSERT INTO `uuser` VALUES ('20', '1', '25', '3585225', 'chenjie19891109', 'chenjie19891109', '2015-04-28 15:19:49', '2015-04-28 15:19:49', '1430205589927.appchina', 'e92002c0f5f19598f9a49b55fd57f3d1');
INSERT INTO `uuser` VALUES ('21', '1', '26', '2391023', 'ydld_0547729022', '', '2015-04-28 20:48:13', '2015-04-28 20:48:13', '1430225293775.cloudpoint', '84244201d8e962fa1660918b0c58b41e');
INSERT INTO `uuser` VALUES ('22', '1', '10', '1724519942', '1724519942', '九游玩家537886537', '2015-04-28 21:16:19', '2015-06-30 20:39:09', '1430226979251.uc', '4999b685ee4423597145de8b5b406dc9');
INSERT INTO `uuser` VALUES ('23', '1', '27', '20150430135342ibmqnatu5i', '', '', '2015-04-30 14:07:57', '2015-04-30 14:07:57', '1430374077861.anzhi', '4ed9bf0768873f9aee64b8b686e642bf');
INSERT INTO `uuser` VALUES ('24', '1', '28', '7473756', 'fhdhdhdh', '', '2015-04-30 15:13:27', '2015-04-30 15:13:27', '1430378007315.mumayi', 'c582511173dcc04d9e63f7ce456f5fcd');
INSERT INTO `uuser` VALUES ('25', '1', '30', '2606856', 'J187994162', 'J187994162', '2015-04-30 16:51:47', '2015-05-04 10:49:14', '1430383907741.paojiao', 'aa2ce48b7dfa7251ff583349f4d880fd');
INSERT INTO `uuser` VALUES ('26', '1', '29', '0S5I3N2KCJLQD8CI', 'sdfdsf', '', '2015-05-04 11:50:39', '2015-05-04 11:51:36', '1430711439348.guopan', 'cd8ea3aa201dcd609f197b1da456d4fe');
INSERT INTO `uuser` VALUES ('27', '1', '31', '16409735', '', '', '2015-05-04 15:25:18', '2015-05-04 15:48:21', '1430724318289.meizu', 'e60a3c305791b1e2e3e94c7da6df650c');
INSERT INTO `uuser` VALUES ('28', '1', '33', '291628d0790721ca', '', '', '2015-05-04 21:01:11', '2015-05-05 09:14:06', '1430744471243.ouwan', 'a768d2b637b7afa2d8ddae30d240623a');
INSERT INTO `uuser` VALUES ('29', '1', '34', 'E59EDE98622248A3B8B6BBE187656B5C', 'Amigo_44104', '', '2015-05-06 09:48:32', '2015-05-06 10:55:01', '1430876912124.am', '4c79ec0ee4c0a1e2405b478f4789904d');
INSERT INTO `uuser` VALUES ('30', '1', '32', '29682286', 'CCNK1428383254363', 'CCNK1428383254363', '2015-05-06 11:09:57', '2015-05-11 16:07:07', '1430881797104.coolpad', 'f1d235e26bc9427602f7ae2ee68351e9');
INSERT INTO `uuser` VALUES ('31', '1', '10', '1324439506', '1324439506', '九游玩家_ib0i44ofb2', '2015-06-17 17:18:42', '2015-06-17 17:18:42', '1434532722195.uc', '03a60052c404ff5ac65d77fb1cb6dd6b');
INSERT INTO `uuser` VALUES ('32', '1', '12', '2003198165', '', '', '2015-06-17 17:24:55', '2015-06-17 17:25:32', '1434533095191.baidu', 'd50087b987e7c52f44ba7a33ebbd0012');
INSERT INTO `uuser` VALUES ('33', '1', '12', '1292623589', '', '', '2015-06-17 17:25:53', '2015-07-06 15:14:37', '1434533153312.baidu', '9966f8d9cc000217c37d1102c617eb17');
INSERT INTO `uuser` VALUES ('34', '1', '24', '900086000023101422', '182****6067', '', '2015-06-18 11:08:20', '2015-06-18 11:08:20', '1434596900138.huawei', '6b2e92306f0fdb71852b82ab761eaf59');
