import CommunitySideBar from "./CommunitySideBar";

type CommunityLayoutProps = {
  children: React.ReactNode;
};
function CommunityLayout({ children }: CommunityLayoutProps) {
  return (
    <section className="min-h-screen flex h-full">
      <CommunitySideBar />
      {children}
    </section>
  );
}

export default CommunityLayout;
