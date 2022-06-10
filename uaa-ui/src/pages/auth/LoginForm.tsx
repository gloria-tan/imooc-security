
export const LoginForm = () => {

    function onLogin() : void {
        console.log("login");
    }

    return (
        <form method="post" onSubmit={(evt) => onLogin()}>
            <input type="text" name="username" id="username" />

            <input type="password" name="password" id="password"/>

            <button type="submit">Submit</button>
        </form>
    );
}