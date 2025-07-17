def generate_random_string(self, length=8, chars='[LETTERS][NUMBERS]'):

        | = Marker = | = Explanation = |
        | ``[LOWER]`` | Lowercase ASCII characters from ``a`` to ``z``. |
        | ``[UPPER]`` | Uppercase ASCII characters from ``A`` to ``Z``. |
        | ``[LETTERS]`` | Lowercase and uppercase ASCII characters. |
        | ``[NUMBERS]`` | Numbers from 0 to 9. |

        Examples:
        | ${ret} = | Generate Random String |
        | ${low} = | Generate Random String | 12 | [LOWER] |
        | ${bin} = | Generate Random String | 8 | 01 |
        | ${hex} = | Generate Random String | 4 | [NUMBERS]abcdef |
        # Generates a string 5 to 10 characters long |
        | ${rnd} = | Generate Random String | 5-10 |

        Giving ``length`` as a range of values is new in Robot Framework 5.0.
        """
    if length == '':
            length = 8
        if isinstance(length, str) and re.match(r'^\d+-\d+$', length):
            min_length, max_length = length.split('-')
            length = randint(self._convert_to_integer(min_length, "length"),
                             self._convert_to_integer(max_length, "length"))
        else:
            length = self._convert_to_integer(length, 'length')
        for name, value in [('[LOWER]', ascii_lowercase),
                            ('[UPPER]', ascii_uppercase),
                            ('[LETTERS]', ascii_lowercase + ascii_uppercase),
                            ('[NUMBERS]', digits)]:
            chars = chars.replace(name, value)
        maxi = len(chars) - 1
        return ''.join(chars[randint(0, maxi)] for _ in range(length)) 