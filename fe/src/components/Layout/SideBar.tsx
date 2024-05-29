import Logo from "@/assets/img/Logo.png";

function SideBar() {
  return (
    <>
      <article className="w-1/12 bg-slate-400 min-h-screen">
        <img src={Logo} alt="" />
      </article>
    </>
  );
}

export default SideBar;
