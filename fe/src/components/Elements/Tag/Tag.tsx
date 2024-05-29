interface TagProps {
  color: "blue" | "red" | "green" | "yellow";
  children: React.ReactNode;
}
export const Tag = ({ color, children }: TagProps) => {
  const colorVariations: {
    blue: string;
    red: string;
    green: string;
    yellow: string;
  } = {
    blue: "bg-blue-50 border-blue-400 text-blue-400",
    red: "bg-red-50 border-red-400 text-red-400",
    green: "bg-green-50 border-green-400 text-green-400",
    yellow: "bg-yellow-50 border-yellow-400 text-yellow-400",
  };

  return (
    <span className={`mr-3 border-1 ${colorVariations[color]} rounded-md px-1`}>
      {children}
    </span>
  );
};
