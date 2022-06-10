
export const MfaForm = () => {

    function onSubmit() {
        console.log("On submit");
    }

    return (
        <form  method="post" onSubmit={(evt) => onSubmit() }>
            <label htmlFor="">发送方式</label>

            <input type="radio" name="sms" id="sms" value="sms" />
            <label htmlFor="sms">短信</label>

            <input type="radio" name="email" id="email" value="email" checked />
            <label htmlFor="email">电子邮件</label>

            <span>*</span>
            <label htmlFor="mfacode">验证码</label>
            <input type="text" name="mfacode" id="mfacode" />
            <button>发送验证码</button>

            <button type="submit">验证并登录</button>
        </form>
    );
}