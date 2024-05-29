module.exports = {
	root: true,
	env: { browser: true, es2020: true },
	extends: [
		"eslint:recommended",
		"plugin:@typescript-eslint/recommended",
		"plugin:react-hooks/recommended",
		"plugin:storybook/recommended",
		"plugin:react/recommended",
		"plugin:jsx-ally/recommended",
		"plugin:prettier/recommended",
	],
	ignorePatterns: ["dist", ".eslintrc.cjs"],
	parser: "@typescript-eslint/parser",
	parserOptions: {
		ecmaVersion: "latest",
		sourceType: "module",
		ecmaFeatures: {
			jsx: true,
			tsx: true,
		},
	},
	plugins: ["react-refresh", "react", "react-hooks", "jsx-a11y"],
	rules: {
		"react-refresh/only-export-components": ["warn", { allowConstantExport: true }],
	},
	setting: {
		react: { version: "detect" },
	},
};
