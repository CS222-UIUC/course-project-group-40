import pathlib


# LOAD DATA
def load(file_path: str):
    file_path = pathlib.Path(file_path)
    func_dict = {'.txt': load_txt, '.list': load_txt}
    assert file_path.suffix in func_dict
    return func_dict[file_path.suffix](file_path)


# LOAD DATA FOR TXT FORMAT
def load_txt(file_path: str):
    with open(file_path, 'r', encoding='utf8') as f:
        content = [x.strip().strip('\ufeff').strip('\xef\xbb\xbf')
                   for x in f.readlines()]
    return content
