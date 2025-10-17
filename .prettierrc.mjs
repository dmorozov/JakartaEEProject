const config = {
  printWidth: 120,
  singleQuote: true,
  trailingComma: "es5",
  tabWidth: 2,
  useTabs: false,
  singleAttributePerLine: false,
  semi: true,
  bracketSpacing: true,
  // arrowParens: 'always',
  // bracketSameLine: false,
  // experimentalTernaries: false,
  // jsxSingleQuote: true,
  // quoteProps: 'as-needed',
  // htmlWhitespaceSensitivity: 'css',
  // vueIndentScriptAndStyle: false,
  // proseWrap: 'preserve',
  // insertPragma: false,
  // requirePragma: false,
  // embeddedLanguageFormatting: 'auto',
  overrides: [
    {
      files: "*.mdx",
      options: {
        printWidth: 70,
      },
    },
  ],
  plugins: ["prettier-plugin-jsp"],
};

export default config;
