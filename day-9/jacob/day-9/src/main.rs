use std::fs::File;
use std::io::{self, BufRead};
use std::path::Path;

fn main() {
    if let Ok(lines) = read_lines("./input.txt") {
        let mut histories: Vec<Vec<i64>> = vec![];
        for line in lines {
            if let Ok(line_str) = line {
                let mut history: Vec<i64> = line_str.split(" ").map(|x| x.parse::<i64>().unwrap()).collect();
                history.reverse();
                histories.push(history);
            }
        }
        
        let mut sum = 0;
        for history in histories {
            let mut i = history.len() - 1;
            let mut rows: Vec<Vec<i64>> = vec![];
            let mut curr = -(history[i] - history[i-1]);
            let mut depth = 1;
            'outer: loop{
                if rows.len() < depth {
                    rows.push(vec![]);
                }
                let curr_row = rows.get_mut(depth - 1).unwrap();
                curr_row.push(curr);

                if curr_row.len() > 1 {
                    curr = -(curr_row[curr_row.len() - 2] - curr);
                    depth += 1;

                    if depth == history.len() - 1{
                        break 'outer;
                    }

                } else {
                    i -= 1;
                    curr = -(history[i] - history[i-1]);
                    depth = 1;
                }
            }

            // for row in rows {
            //     for item in row {
            //         print!("{} ", item);
            //     }
            //     println!();
            // }

            let mut last = 0 ;
            for idx in 0..rows.len() {
                last = rows[rows.len() - idx - 1].first().unwrap() - last;
            }
            
            sum += history[history.len() - 1] - last;
        }
        println!("{}", sum);
    }
}

// The output is wrapped in a Result to allow matching on errors
// Returns an Iterator to the Reader of the lines of the file.
fn read_lines<P>(filename: P) -> io::Result<io::Lines<io::BufReader<File>>>
where P: AsRef<Path>, {
    let file = File::open(filename)?;
    Ok(io::BufReader::new(file).lines())
}
