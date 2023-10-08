import Head from 'next/head';
import Link from 'next/link';
import Login from '../components/Login';

const LoginPage = () => {
  return (
    <div>
      <Head>
        <title>Login</title>
      </Head>

      <main>
        <Login />
        <Link href="/register">
          Don't have an account?
        </Link>
      </main>
    </div>
  );
};

export default LoginPage;
