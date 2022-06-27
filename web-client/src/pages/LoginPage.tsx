import {FormEvent, FormEventHandler} from "react";

export const LoginPage = () => {

    const onInternalUserLogin: FormEventHandler<HTMLFormElement> = (event) => {
        event.preventDefault();
        const username: HTMLInputElement = event.currentTarget.elements.namedItem("username") as HTMLInputElement;
        console.log("username " + username.value);

        const password: HTMLInputElement = event.currentTarget.elements.namedItem("password") as HTMLInputElement;
        console.log("password " + password.value);

    }

    return (
        <div className="max-w-md mx-auto">
            <h2 className={"text-2xl font-bold text-gray-900 m-6"}>登录</h2>

            <form onSubmit={onInternalUserLogin} className={"flex flex-col p-3 mb-6 border-solid border-2 border-indigo-600"}>
                <label htmlFor="username">用户名</label>
                <input type="text" id="username" name="username" className={"border-solid border-2 rounded-lg"}/>

                <label htmlFor="password">密码</label>
                <input type="password" id="password" name="password"/>

                <button type="submit" className={"btn py-3"}>使用内置用户登录</button>
            </form>

            <h2>使用不同的grant_type授权客户端</h2>
            <table>
                <tbody>
                <tr>
                    <td>
                        <a href="#">授权码流程 <span>（使用: user/12345678 登录 UAA）</span> </a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="#">客户端凭证流程</a>
                    </td>
                </tr>

                <tr>
                    <td>
                        <form>
                            <h3>资源所以者密码流程</h3>

                            <label htmlFor="ownername">用户名</label>
                            <input type="text" id="ownername" name="ownername"/>

                            <label htmlFor="ownerpassword">密码</label>
                            <input type="password" id="ownerpassword" name="ownerpassword"/>

                            <button type="submit">授权</button>
                        </form>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    );
}