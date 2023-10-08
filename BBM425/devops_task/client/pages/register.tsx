import Head from 'next/head';
import Link from 'next/link';
import Register from '../components/Register';

const RegisterPage = () => {
  return (
    <div>
      <Head>
        <title>Register</title>
      </Head>

      <main>
        <Register />
        <Link href="/login">
          Have an account?
        </Link>
      </main>
    </div>
  );
};

export default RegisterPage;
